package controller;

import data_access.CourseDataAccess;
import data_access.ReportDataAccess;
import data_access.UserDataAccess;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Course;

public class AdminHomeController extends HttpServlet {
    private CourseDataAccess courseDAO;
    private UserDataAccess userDAO;
    private ReportDataAccess reportDAO;

    @Override
    public void init() throws ServletException {
        courseDAO = CourseDataAccess.getInstance();
        userDAO = UserDataAccess.getInstance();
        reportDAO = ReportDataAccess.getInstance();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Course> courseList = courseDAO.getAllCourses();
        request.setAttribute("courses_size", courseList.size());
        request.setAttribute("active_size", courseDAO.getAllActiveCourses().size());
        request.setAttribute("user_size", userDAO.getAllUsers().size());
        request.setAttribute("total_profit", reportDAO.getTotalProfit());
        request.setAttribute("report_list", reportDAO.getAllReports());
        request.setAttribute("course_list", courseList);
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
            courseDAO.setCourseStatus(courseId, true);
        }
        else if (action.equals("deactivate_course")) {
            courseDAO.setCourseStatus(courseId, false);
        }
        response.sendRedirect(request.getContextPath() + "/admin/home");
    }
}
