/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import data_access.Account_DAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import model.User;

/**
 *
 * @author LONG
 */
public class LoginController extends HttpServlet {
    private Account_DAO dao;
    
    @Override
    public void init() throws ServletException {
        dao = new Account_DAO();
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
            response.sendRedirect(request.getContextPath()+"/login");
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
    
    

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
