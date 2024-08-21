package controller;

import data_access.CourseDataAccess;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class IndexController extends HttpServlet {
    private CourseDataAccess dao;

    @Override
    public void init() throws ServletException {
        dao = CourseDataAccess.getInstance();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("new_courses", dao.getTopNewCourses(4));
        request.setAttribute("best_sellers", dao.getTopSellCourses(4));
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
