package controller;

import data_access.CourseDataAccess;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import model.CourseContent;

public class AdminManageCourseContentController extends HttpServlet {
    private CourseDataAccess dao;

    @Override
    public void init() throws ServletException {
        dao = CourseDataAccess.getInstance();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            response.sendError(404);
            return;
        }
        String courseIdStr = request.getParameter("course_id");
        if (courseIdStr == null || !courseIdStr.matches("^[0-9]+$")) {
            response.sendError(404);
            return;
        }
        int courseId = Integer.parseInt(courseIdStr);
        if (action.equals("add_quiz")) {
            Part filePart = request.getPart("quiz_file");
            if (filePart != null && filePart.getSize() > 0) {
                StringBuilder fileContent = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(filePart.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        fileContent.append(line);
                    }
                }
                String title = request.getParameter("title");
                dao.addCourseContent(courseId, CourseContent.Type.Quiz, title, fileContent.toString());
            }
        }
        else if (action.equals("add_lesson")) {
            String title = request.getParameter("title");
            String content = request.getParameter("lesson_content");
            dao.addCourseContent(courseId, CourseContent.Type.Lesson, title, content);
        }
        else if (action.equals("update_lesson")) {
            String idStr = request.getParameter("id");
            if (idStr != null && idStr.matches("^[0-9]+$")) {
                String title = request.getParameter("title");
                String content = request.getParameter("lesson_content");
                dao.updateCourseContent(Integer.parseInt(idStr), title, content);
            }
        }
        else if (action.equals("delete")) {
            String idStr = request.getParameter("id");
            if (idStr != null && idStr.matches("^[0-9]+$")) {
                dao.deleteCourseContent(Integer.parseInt(idStr));
            }
        }
        response.sendRedirect(request.getContextPath() + "/admin/edit_course?id=" + courseId);
    }
}
