package controller;

import data_access.CourseDataAccess;
import data_access.EnrollDataAccess;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.CourseContent;
import model.User;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

public class LessonController extends HttpServlet {
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
        int id = Integer.parseInt(idStr);
        CourseContent content = courseDAO.getContentById(id);
        if (content.getType() != CourseContent.Type.Lesson) {
            response.sendError(404);
            return;
        }
        Parser parser = Parser.builder().build();
        Node document = parser.parse(content.getContent());
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        content.setContent(renderer.render(document));
        User user = (User)request.getSession().getAttribute("user");
        request.setAttribute("completed", enrollDAO.isLessonCompleted(content.getId(), user.getId()));
        request.setAttribute("lesson", content);
        request.getRequestDispatcher("lesson.jsp").forward(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr == null || !idStr.matches("^[0-9]+$")) {
            response.sendError(404);
            return;
        }
        int id = Integer.parseInt(idStr);
        CourseContent lesson = courseDAO.getContentById(id);
        if (lesson == null || lesson.getType() != CourseContent.Type.Lesson) {
            response.sendError(404);
            return;
        }
        User user = (User)request.getSession().getAttribute("user");
        enrollDAO.addLessonProgress(lesson.getId(), user.getId());
        enrollDAO.checkCourseCompleted(lesson.getCourseId(), user.getId());
        response.sendRedirect(request.getContextPath() + "/course_detail?id=" + lesson.getCourseId());
    }
}
