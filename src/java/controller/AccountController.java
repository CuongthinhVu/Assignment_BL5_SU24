package controller;

import data_access.UserDataAccess;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import model.User;

public class AccountController extends HttpServlet {
    private UserDataAccess dao;

    @Override
    public void init() throws ServletException {
        dao = UserDataAccess.getInstance();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("account.jsp").forward(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            response.sendError(404);
            return;
        }
        User user = (User)request.getSession().getAttribute("user");
        if (action.equals("change_pw")) {
            String oldPassword = request.getParameter("old_password");
            String newPassword = request.getParameter("new_password");
            User check = dao.login(user.getEmail(), oldPassword);
            if (check == null) {
                response.sendRedirect(request.getContextPath() + "/account?err=" + URLEncoder.encode("Wrong password!", "UTF-8"));
            }
            else {
                dao.changePassword(user.getId(), newPassword);
                response.sendRedirect(request.getContextPath() + "/logout");
            }
        }
        else if (action.equals("delete_account")) {
            dao.deleteAccount(user.getId());
            response.sendRedirect(request.getContextPath() + "/logout");
        }
    }
}
