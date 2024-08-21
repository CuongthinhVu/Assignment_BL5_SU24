package controller;

import data_access.CourseDataAccess;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Set;
import java.util.Arrays;
import java.util.HashSet;
import model.Paging;
import model.Course;

public class CourseController extends HttpServlet {
    private CourseDataAccess courseDAO;
    private static final int ITEMS_PER_PAGE = 6;

    @Override
    public void init() throws ServletException {
        courseDAO = CourseDataAccess.getInstance();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Course> courseList = courseDAO.getAllActiveCourses();
        
        String sortStr = request.getParameter("sort") == null ? "release_new" : request.getParameter("sort");
        switch (sortStr) {
            case "release_new":
                courseList.sort((c1, c2) -> c2.getCreatedAt().compareTo(c1.getCreatedAt()));
                break;
            case "release_old":
                courseList.sort((c1, c2) -> c1.getCreatedAt().compareTo(c2.getCreatedAt()));
                break;
            case "price_low":
                courseList.sort((c1, c2) -> Integer.compare(c1.getSalePrice(), c2.getSalePrice()));
                break;
            case "price_high":
                courseList.sort((c1, c2) -> Integer.compare(c2.getSalePrice(), c1.getSalePrice()));
                break;
            default:
                break;
        }
        
        String searchStr = request.getParameter("search");
        if (searchStr != null) {
            courseList = courseList.stream().filter(course -> course.getTitle().contains(searchStr)).toList();
        }
        
        String[] categoryIdsStr = request.getParameterValues("category");
        if (categoryIdsStr != null) {
            Set<Integer> cateIds = new HashSet<>();
            for (String idStr : categoryIdsStr) {
                try {
                    int id = Integer.parseInt(idStr);
                    cateIds.add(id);
                } catch (NumberFormatException e) {
                }
            }
            if (!cateIds.isEmpty()) {
                courseList = courseList.stream()
                        .filter(course -> course.getCategories().stream()
                                .anyMatch(category -> cateIds.contains(category.getId())))
                        .toList();
                request.setAttribute("checked_categories", cateIds);
            }
        }
        
        String[] priceRangesStr = request.getParameterValues("price_range");
        if (priceRangesStr != null) {
            courseList = courseList.stream().filter(course -> {
                boolean oke = false;
                for (String priceRange : priceRangesStr) {
                    String[] parts = priceRange.split("_");
                    if (parts[0].isEmpty()) {
                        int max = Integer.parseInt(parts[1]);
                        if (course.getSalePrice() < max) {
                            oke = true;
                        }
                    }
                    else if (parts.length < 2 || parts[1].isEmpty()) {
                        int min = Integer.parseInt(parts[0]);
                        if (course.getSalePrice() > min) {
                            oke = true;
                        }
                    }
                    else {
                        int min = Integer.parseInt(parts[0]);
                        int max = Integer.parseInt(parts[1]);
                        if (course.getSalePrice() >= min && course.getSalePrice() <= max) {
                            oke = true;
                        }
                    }
                }
                return oke;
            }).toList();
            request.setAttribute("checked_prices", priceRangesStr);
        }
        
        int currentPage = 0;
        String currPageStr = request.getParameter("page");
        if (currPageStr != null && currPageStr.matches("^[0-9]+$")) {
            currentPage = Integer.parseInt(currPageStr);
        }
        Paging page = new Paging(courseList.size(), ITEMS_PER_PAGE, currentPage);
        page.calc();
        
        request.setAttribute("page", page);
        request.setAttribute("category_list", courseDAO.getAllCategories());
        request.setAttribute("course_list", courseList);
        request.getRequestDispatcher("courses.jsp").forward(request, response);
    }
}
