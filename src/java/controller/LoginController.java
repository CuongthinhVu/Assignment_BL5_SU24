package controller;

import data_access.UserDataAccess;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import model.User;

public class LoginController extends HttpServlet {
    private UserDataAccess dao;
    
    @Override
    public void init() throws ServletException {
        dao = UserDataAccess.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String remember_me = request.getParameter("remember_me");
        User user = dao.login(email,password);
        if(user==null){
            response.sendRedirect(request.getContextPath() + "/login?err=" + URLEncoder.encode("Wrong email or password!", "UTF-8"));
            return;
        }
        
        if (remember_me != null && remember_me.equals("true")) {
            Cookie userCookie = new Cookie("user_login", email);
            userCookie.setMaxAge(60 * 60 * 24 * 7);
            response.addCookie(userCookie);
        }
        request.getSession().setAttribute("user", user);
        response.sendRedirect(request.getContextPath());
    }
}
