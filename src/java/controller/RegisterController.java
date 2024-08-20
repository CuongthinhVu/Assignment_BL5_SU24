package controller;

import data_access.UserDataAccess;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

public class RegisterController extends HttpServlet {
    private UserDataAccess dao;

    @Override
    public void init() throws ServletException {
        dao = UserDataAccess.getInstance();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        if (!dao.register(email, password)) {
            response.sendRedirect(request.getContextPath() + "/register?err=" + URLEncoder.encode("Register failed", "UTF-8"));
            return;
        }
        response.sendRedirect(request.getContextPath() + "/login");
    }
}
