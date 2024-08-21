package filter;

import data_access.CourseDataAccess;
import data_access.EnrollDataAccess;
import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Course;
import model.Enrollment;
import model.User;

public class EnrollFilter implements Filter {
    private CourseDataAccess courseDAO;
    private EnrollDataAccess enrollDAO;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        courseDAO = CourseDataAccess.getInstance();
        enrollDAO = EnrollDataAccess.getInstance();
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        User user = (User)req.getSession().getAttribute("user");
        String courseIdStr = req.getParameter("course_id");
        if (courseIdStr == null || !courseIdStr.matches("^[0-9]+$")) {
            resp.sendError(404);
            return;
        }
        int courseId = Integer.parseInt(courseIdStr);
        Course course = courseDAO.getCourseById(courseId);
        if (course == null) {
            resp.sendError(404);
            return;
        }
        Enrollment en = enrollDAO.getEnrollment(courseId, user.getId());
        boolean isEnrolled = en != null;
        String path = req.getServletPath();
        if (path.equals("/checkout") && isEnrolled) {
            resp.sendRedirect(req.getContextPath() + "/course_detail?id=" + courseId);
            return;
        }
        if ((path.equals("/lesson") || path.equals("/quiz")) && !isEnrolled) {
            resp.sendRedirect(req.getContextPath() + "/course_detail?id=" + courseId);
            return;
        }
        chain.doFilter(request, response);
    }
}
