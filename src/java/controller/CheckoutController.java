package controller;

import data_access.CourseDataAccess;
import data_access.EnrollDataAccess;
import data_access.ReportDataAccess;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Course;
import model.User;

public class CheckoutController extends HttpServlet {
    private CourseDataAccess courseDAO;
    private EnrollDataAccess enrollDAO;
    private ReportDataAccess reportDAO;

    @Override
    public void init() throws ServletException {
        courseDAO = CourseDataAccess.getInstance();
        enrollDAO = EnrollDataAccess.getInstance();
        reportDAO = ReportDataAccess.getInstance();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("course_id");
        if (idStr == null || !idStr.matches("^[0-9]+$")) {
            response.sendError(404);
            return;
        }
        int id = Integer.parseInt(idStr);
        Course course = courseDAO.getCourseById(id);
        if (course == null || !course.isActive()) {
            response.sendError(404);
            return;
        }
        User user = (User)request.getSession().getAttribute("user");
        enrollDAO.addPayment(course.getId(), course.getTitle(), user.getId(), course.getSalePrice());
        enrollDAO.addEnrollment(course.getId(), user.getId());
        reportDAO.userPurchaseCourse(course.getId(), course.getSalePrice());
        request.setAttribute("course_id", course.getId());
        request.getRequestDispatcher("payment_result.jsp").forward(request, response);
    }
}
