package controller;

import data_access.CourseDataAccess;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import model.Course;
import model.CourseContent;
import util.ImageUtil;

public class AdminEditCourseController extends HttpServlet {
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
        String contentStr = request.getParameter("content");
        if (contentStr != null && contentStr.matches("^[0-9]+$")) {
            CourseContent content = dao.getContentById(Integer.parseInt(contentStr));
            if (content.getType() == CourseContent.Type.Lesson) {
                request.setAttribute("select_content", content);
            }
        }
        Course course = dao.getCourseById(Integer.parseInt(idStr));
        request.setAttribute("course", course);
        request.setAttribute("category_list", dao.getAllCategories());
        request.setAttribute("content_list", dao.getAllContentsOfCourse(course.getId()));
        request.getRequestDispatcher("/WEB-INF/admin/edit_course.jsp").forward(request, response);
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
        if (action.equals("update_thumbnail")) {
            Course course = dao.getCourseById(courseId);
            Part filePart = request.getPart("thumbnail");
            String imageName;
            if (filePart != null && filePart.getSize() > 0) {
                String imagePath = getServletContext().getRealPath("/") + "img";
                imageName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                ImageUtil.deleteImage(imagePath + "/" + course.getImagePath());
                ImageUtil.uploadImage(imagePath, imageName, filePart.getInputStream());
                dao.updateCourseImagePath(courseId, imageName);
            }
        }
        else if (action.equals("update_meta")) {
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            String price = request.getParameter("price");
            String[] catArr = request.getParameterValues("categories");
            List<Integer> categories = new ArrayList<>();
            for (String id : catArr) {
                categories.add(Integer.valueOf(id));
            }
            dao.updateCourse(courseId, title, description, Integer.parseInt(price), categories);
        }
        response.sendRedirect(request.getContextPath() + "/admin/edit_course?id=" + courseId);
    }
}
