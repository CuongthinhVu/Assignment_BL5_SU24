package controller;

import data_access.CourseDataAccess;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AdminHomeController extends HttpServlet {
    private CourseDataAccess dao;

    @Override
    public void init() throws ServletException {
        dao = CourseDataAccess.getInstance();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("course_list", dao.getAllCourses());
        request.getRequestDispatcher("/WEB-INF/admin/home.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            response.sendError(404);
            return;
        }
        String courseIdStr = request.getParameter("id");
        if (courseIdStr == null || !courseIdStr.matches("^[0-9]+$")) {
            response.sendError(404);
            return;
        }
        int courseId = Integer.parseInt(courseIdStr);
        if (action.equals("activate_course")) {
            dao.setCourseStatus(courseId, true);
        }
        else if (action.equals("deactivate_course")) {
            dao.setCourseStatus(courseId, false);
        }
        response.sendRedirect(request.getContextPath() + "/admin/home");
    }
}
