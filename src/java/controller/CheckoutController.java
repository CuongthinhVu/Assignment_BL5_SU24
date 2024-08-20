package controller;

import data_access.CourseDataAccess;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Course;

public class CheckoutController extends HttpServlet {
    private CourseDataAccess dao;

    @Override
    public void init() throws ServletException {
        dao = CourseDataAccess.getInstance();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr == null || !idStr.matches("^[0-9]+$")) {
            response.sendError(404);
            return;
        }
        int id = Integer.parseInt(idStr);
        Course course = dao.getCourseById(id);
        request.setAttribute("course_id", course.getId());
        request.getRequestDispatcher("payment_result.jsp").forward(request, response);
    }
}
