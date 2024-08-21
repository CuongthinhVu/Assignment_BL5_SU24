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
import util.ImageUtil;

public class AdminAddCourseController extends HttpServlet {
    private CourseDataAccess dao;

    @Override
    public void init() throws ServletException {
        dao = CourseDataAccess.getInstance();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("category_list", dao.getAllCategories());
        request.getRequestDispatcher("/WEB-INF/admin/add_course.jsp").forward(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            response.sendError(404);
            return;
        }
        if (action.equals("add_category")) {
            String category = request.getParameter("new_category");
            dao.addCategory(category);
            response.sendRedirect(request.getContextPath() + "/admin/add_course");
        }
        else if (action.equals("delete_category")) {
            String categoryIdStr = request.getParameter("category");
            if (categoryIdStr == null || !categoryIdStr.matches("^[0-9]+$")) {
                response.sendError(404);
                return;
            }
            int id = Integer.parseInt(categoryIdStr);
            dao.deleteCategory(id);
            response.sendRedirect(request.getContextPath() + "/admin/add_course");
        }
        else if (action.equals("add_course")) {
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            String price = request.getParameter("price");
            Part filePart = request.getPart("thumbnail");
            String imageName = null;
            if (filePart != null && filePart.getSize() > 0) {
                String imagePath = getServletContext().getRealPath("/") + "img";
                imageName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                ImageUtil.uploadImage(imagePath, imageName, filePart.getInputStream());
            }
            String[] catArr = request.getParameterValues("categories");
            List<Integer> categories = new ArrayList<>();
            for (String id : catArr) {
                categories.add(Integer.valueOf(id));
            }
            dao.addCourse(title, description, Integer.parseInt(price), imageName, false, categories);
            response.sendRedirect(request.getContextPath() + "/admin/home");
        }
    }
}
