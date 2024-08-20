package data_access;

import java.util.ArrayList;
import model.Course;
import java.util.List;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import model.Category;
import model.CourseContent;

public class CourseDataAccess {
    private static CourseDataAccess INSTANCE;
    
    private CourseDataAccess() {
    }
    
    public static CourseDataAccess getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CourseDataAccess();
        }
        return INSTANCE;
    }
    
    public List<Course> getAllCourses() {
        String sql = """
                     select c.*, cat.[id] as category_id, cat.[name] as category_name
                     from [course] as c
                     left join [course_category] as cc on c.[id] = cc.[course_id]
                     left join [category] as cat on cc.[category_id] = cat.[id]
                     order by c.[id], cat.[id];
                     """;
        List<Course> list = new ArrayList<>();
        Course curr = null;
        int lastId = -1;
        try (PreparedStatement statement = DBContext.getConnection().prepareStatement(sql)) {
            statement.execute();
            ResultSet res = statement.getResultSet();
            while (res.next()) {
                int courseId = res.getInt("id");
                if (curr == null || lastId != courseId) {
                    curr = new Course();
                    curr.setId(courseId);
                    curr.setTitle(res.getString("title"));
                    curr.setDescription(res.getString("description"));
                    curr.setOriginalPrice(res.getInt("original_price"));
                    curr.setSalePrice(res.getInt("sale_price"));
                    curr.setImagePath(res.getString("image_path"));
                    curr.setActive(res.getBoolean("active"));
                    curr.setCreatedAt(res.getTimestamp("created_at").toLocalDateTime());
                    curr.setCategories(new ArrayList<>());
                    list.add(curr);
                    lastId = courseId;
                }
                int categoryId = res.getInt("category_id");
                if (categoryId != 0) {
                    curr.getCategories().add(new Category(categoryId, res.getString("category_name")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<Category> getAllCategories() {
        String sql = "select * from [category];";
        List<Category> list = new ArrayList<>();
        try (PreparedStatement statement = DBContext.getConnection().prepareStatement(sql)) {
            statement.execute();
            ResultSet res = statement.getResultSet();
            while (res.next()) {
                int id = res.getInt("id");
                String name = res.getString("name");
                list.add(new Category(id, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public Course getCourseById(int id) {
        String sql = """
                     select c.*, cat.[id] as category_id, cat.[name] as category_name
                     from [course] as c
                     left join [course_category] as cc on c.[id] = cc.[course_id]
                     left join [category] as cat on cc.[category_id] = cat.[id]
                     where c.[id] = ?
                     order by c.[id], cat.[id];
                     """;
        Course course = null;
        try (PreparedStatement statement = DBContext.getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.execute();
            ResultSet res = statement.getResultSet();
            while (res.next()) {
                if (course == null) {
                    course = new Course();
                    course.setId(res.getInt("id"));
                    course.setTitle(res.getString("title"));
                    course.setDescription(res.getString("description"));
                    course.setOriginalPrice(res.getInt("original_price"));
                    course.setSalePrice(res.getInt("sale_price"));
                    course.setImagePath(res.getString("image_path"));
                    course.setActive(res.getBoolean("active"));
                    course.setCreatedAt(res.getTimestamp("created_at").toLocalDateTime());
                    course.setCategories(new ArrayList<>());
                }
                int categoryId = res.getInt("category_id");
                if (categoryId != 0) {
                    course.getCategories().add(new Category(categoryId, res.getString("category_name")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return course;
    }
    
    public List<CourseContent> getAllContentsOfCourse(int courseId) {
        String sql = "select * from [course_content] where [course_id] = ?;";
        List<CourseContent> list = new ArrayList<>();
        try (PreparedStatement statement = DBContext.getConnection().prepareStatement(sql)) {
            statement.setInt(1, courseId);
            statement.execute();
            ResultSet res = statement.getResultSet();
            while (res.next()) {
                int id = res.getInt("id");
                int cid = res.getInt("course_id");
                CourseContent.Type type = CourseContent.Type.valueOf(res.getString("type"));
                String title = res.getString("title");
                String content = res.getString("content");
                LocalDateTime createdAt = res.getTimestamp("created_at").toLocalDateTime();
                list.add(new CourseContent(id, cid, type, title, content, createdAt));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public CourseContent getContentById(int id) {
        String sql = "select * from [course_content] where [id] = ?;";
        CourseContent content = null;
        try (PreparedStatement statement = DBContext.getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.execute();
            ResultSet res = statement.getResultSet();
            while (res.next()) {
                int ccid = res.getInt("id");
                int cid = res.getInt("course_id");
                CourseContent.Type type = CourseContent.Type.valueOf(res.getString("type"));
                String title = res.getString("title");
                String contentContent = res.getString("content");
                LocalDateTime createdAt = res.getTimestamp("created_at").toLocalDateTime();
                content = new CourseContent(ccid, cid, type, title, contentContent, createdAt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return content;
    }
    
    public void addCategory(String name) {
        String sql = "insert into [category]([name]) values (?);";
        try (PreparedStatement statement = DBContext.getConnection().prepareStatement(sql)) {
            statement.setString(1, name);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void addCourse(String title, String description, int price, String imagePath, boolean active, List<Integer> categories) {
        String addCourseSQL = "insert into [course]([title], [description], [original_price], [sale_price], [image_path], [active]) values (?, ?, ?, ?, ?, ?);";
        String addCategoriesSQL = "insert into [course_category]([course_id], [category_id]) values (?, ?);";
        try (PreparedStatement courseStmt = DBContext.getConnection().prepareStatement(addCourseSQL, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement cateStmt = DBContext.getConnection().prepareStatement(addCategoriesSQL)) {
            courseStmt.setString(1, title);
            courseStmt.setString(2, description);
            courseStmt.setInt(3, price);
            courseStmt.setInt(4, price);
            courseStmt.setString(5, imagePath);
            courseStmt.setBoolean(6, active);
            courseStmt.executeUpdate();
            ResultSet keys = courseStmt.getGeneratedKeys();
            int courseId;
            if (keys.next()) {
                courseId = keys.getInt(1);
            } else {
                return;
            }
            for (int catId : categories) {
                cateStmt.setInt(1, courseId);
                cateStmt.setInt(2, catId);
                cateStmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void addCourseContent(int courseId, CourseContent.Type type, String title, String content) {
        String sql = "insert into [course_content]([course_id], [type], [title], [content]) values (?, ?, ?, ?);";
        try (PreparedStatement statement = DBContext.getConnection().prepareStatement(sql)) {
            statement.setInt(1, courseId);
            statement.setString(2, type.name());
            statement.setString(3, title);
            statement.setString(4, content);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void updateCourse(int id, String title, String description, int price, List<Integer> categories) {
        String updateCourseSQL = "update [course] set [title] = ?, [description] = ?, [sale_price] = ? where [id] = ?;";
        String deleteCateSQL = "delete from [course_category] where [course_id] = ?;";
        String updateCateSQL = "insert into [course_category]([course_id], [category_id]) values (?, ?);";
        try (PreparedStatement courseStmt = DBContext.getConnection().prepareStatement(updateCourseSQL);
             PreparedStatement cateStmt = DBContext.getConnection().prepareStatement(updateCateSQL);
             PreparedStatement delCateStmt = DBContext.getConnection().prepareStatement(deleteCateSQL)) {
            courseStmt.setString(1, title);
            courseStmt.setString(2, description);
            courseStmt.setInt(3, price);
            courseStmt.setInt(4, id);
            courseStmt.executeUpdate();
            delCateStmt.setInt(1, id);
            delCateStmt.execute();
            for (int catId : categories) {
                cateStmt.setInt(1, id);
                cateStmt.setInt(2, catId);
                cateStmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void updateCourseContent(int id, String title, String content) {
        String sql = "update [course_content] set [title] = ?, [content] = ? where [id] = ?;";
        try (PreparedStatement statement = DBContext.getConnection().prepareStatement(sql)) {
            statement.setString(1, title);
            statement.setString(2, content);
            statement.setInt(3, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void updateCourseImagePath(int id, String imagePath) {
        String sql = "update [course] set [image_path] = ? where [id] = ?;";
        try (PreparedStatement statement = DBContext.getConnection().prepareStatement(sql)) {
            statement.setString(1, imagePath);
            statement.setInt(2, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void deleteCourseContent(int id) {
        String sql = "delete from [course_content] where [id] = ?;";
        try (PreparedStatement statement = DBContext.getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void setCourseStatus(int id, boolean active) {
        String sql = "update [course] set [active] = ? where [id] = ?;";
        try (PreparedStatement statement = DBContext.getConnection().prepareStatement(sql)) {
            statement.setBoolean(1, active);
            statement.setInt(2, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
