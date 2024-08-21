package controller;

import data_access.EnrollDataAccess;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;

public class MyCourseController extends HttpServlet {
    private EnrollDataAccess dao;

    @Override
    public void init() throws ServletException {
        dao = EnrollDataAccess.getInstance();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User)request.getSession().getAttribute("user");
        request.setAttribute("in_progress_courses", dao.getAllInProgressCourses(user.getId()));
        request.setAttribute("completed_courses", dao.getAllCompletedCourses(user.getId()));
        request.getRequestDispatcher("my_courses.jsp").forward(request, response);
    }
}
