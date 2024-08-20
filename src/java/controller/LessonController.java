package controller;

import data_access.CourseDataAccess;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.CourseContent;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

public class LessonController extends HttpServlet {
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
        CourseContent content = dao.getContentById(id);
        if (content.getType() != CourseContent.Type.Lesson) {
            response.sendError(404);
            return;
        }
        Parser parser = Parser.builder().build();
        Node document = parser.parse(content.getContent());
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        content.setContent(renderer.render(document));
        request.setAttribute("lesson", content);
        request.getRequestDispatcher("lesson.jsp").forward(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
}
