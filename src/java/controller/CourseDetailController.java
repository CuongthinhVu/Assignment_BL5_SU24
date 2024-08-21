package controller;

import data_access.CourseDataAccess;
import data_access.EnrollDataAccess;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Enrollment;
import model.QuizResult;
import model.User;

public class CourseDetailController extends HttpServlet {
    private CourseDataAccess courseDAO;
    private EnrollDataAccess enrollDAO;

    @Override
    public void init() throws ServletException {
        courseDAO = CourseDataAccess.getInstance();
        enrollDAO = EnrollDataAccess.getInstance();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr == null || !idStr.matches("^[0-9]+$")) {
            response.sendError(404);
            return;
        }
        int courseId = Integer.parseInt(idStr);
        User user = (User)request.getSession().getAttribute("user");
        if (user != null) {
            Enrollment enroll = enrollDAO.getEnrollment(courseId, user.getId());
            List<Integer> clessons = enrollDAO.getLessonProgress(courseId, user.getId());
            request.setAttribute("lesson_progresses", clessons);
            List<QuizResult> quizResults = enrollDAO.getQuizResult(courseId, user.getId());
            request.setAttribute("quiz_results", quizResults);
            if (enroll != null) {
                request.setAttribute("enroll", enroll);
            }
        }
        request.setAttribute("course", courseDAO.getCourseById(courseId));
        request.setAttribute("content_list", courseDAO.getAllContentsOfCourse(courseId));
        request.getRequestDispatcher("detail.jsp").forward(request, response);
    }
}
