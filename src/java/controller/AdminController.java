package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

public class AdminController extends HttpServlet {
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action != null) {
            if (action.equals("log_out")) {
                request.getSession().removeAttribute("admin");
            }
        }
        request.getRequestDispatcher("/WEB-INF/admin/login.jsp").forward(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || password == null || 
            USERNAME == null || PASSWORD == null || 
            !username.equals(USERNAME) || !password.equals(PASSWORD)) {
            response.sendRedirect(request.getContextPath() + "/admin?err=" + URLEncoder.encode("Wrong username or password", "UTF-8"));
            return;
        }
        request.getSession().setAttribute("admin", username);
        response.sendRedirect(request.getContextPath() + "/admin/home");
    }
}
