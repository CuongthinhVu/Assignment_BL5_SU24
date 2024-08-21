package data_access;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.Enrollment;
import model.QuizResult;

public class EnrollDataAccess {
    private static EnrollDataAccess INSTANCE;
    
    private EnrollDataAccess() {
    }
    
    public static EnrollDataAccess getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EnrollDataAccess();
        }
        return INSTANCE;
    }
    
    public Enrollment getEnrollment(int courseId, int studentId) {
        String sql = "select * from [enrollment] where [course_id] = ? and [student_id] = ?;";
        Enrollment en = null;
        try (PreparedStatement statement = DBContext.getConnection().prepareStatement(sql)) {
            statement.setInt(1, courseId);
            statement.setInt(2, studentId);
            statement.execute();
            ResultSet res = statement.getResultSet();
            while (res.next()) {
                int id = res.getInt("id");
                int cid = res.getInt("course_id");
                int sid = res.getInt("student_id");
                String status = res.getString("status");
                LocalDateTime enrolledAt = res.getTimestamp("enrolled_at").toLocalDateTime();
                en = new Enrollment(id, cid, sid, status, enrolledAt);
                break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return en;
    }
    
    public void addPayment(int courseId, String courseTitle, int studentId, int amount) {
        String sql = "insert into [payment]([course_id], [course_title], [student_id], [amount]) values (?, ?, ?, ?);";
        try (PreparedStatement statement = DBContext.getConnection().prepareStatement(sql)) {
            statement.setInt(1, courseId);
            statement.setString(2, courseTitle);
            statement.setInt(3, studentId);
            statement.setInt(4, amount);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void addEnrollment(int courseId, int studentId) {
        String sql = "insert into [enrollment]([course_id], [student_id]) values (?, ?);";
        try (PreparedStatement statement = DBContext.getConnection().prepareStatement(sql)) {
            statement.setInt(1, courseId);
            statement.setInt(2, studentId);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void addOrUpdateQuizResult(int id, int studentId, int grade, boolean passed) {
        String selectSQL = "select * from [quiz_result] where [quiz_id] = ? and [student_id] = ?;";
        String updateSQL = "update [quiz_result] set [grade] = ?, [passed] = ? where [quiz_id] = ? and [student_id] = ?;";
        String insertSQL = "insert into [quiz_result]([quiz_id], [student_id], [grade], [passed]) values (?, ?, ?, ?);";
        try (PreparedStatement selectStmt = DBContext.getConnection().prepareStatement(selectSQL);
             PreparedStatement updateStmt = DBContext.getConnection().prepareStatement(updateSQL);
             PreparedStatement insertStmt = DBContext.getConnection().prepareStatement(insertSQL)) {
            selectStmt.setInt(1, id);
            selectStmt.setInt(2, studentId);
            selectStmt.execute();
            ResultSet res = selectStmt.getResultSet();
            if (res.next()) {
                int oldGrade = res.getInt("grade");
                if (grade > oldGrade) {
                    updateStmt.setInt(1, grade);
                    updateStmt.setBoolean(2, passed);
                    updateStmt.setInt(3, id);
                    updateStmt.setInt(4, studentId);
                    updateStmt.execute();
                }
            }
            else {
                insertStmt.setInt(1, id);
                insertStmt.setInt(2, studentId);
                insertStmt.setInt(3, grade);
                insertStmt.setBoolean(4, passed);
                insertStmt.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void addLessonProgress(int id, int studentId) {
        String sql = "insert into [lesson_progress]([lesson_id], [student_id]) values (?, ?);";
        try (PreparedStatement statement = DBContext.getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.setInt(2, studentId);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public boolean isLessonCompleted(int lessonId, int studentId) {
        String sql = "select * from [lesson_progress] where [lesson_id] = ? and [student_id] = ?;";
        try (PreparedStatement statement = DBContext.getConnection().prepareStatement(sql)) {
            statement.setInt(1, lessonId);
            statement.setInt(2, studentId);
            statement.execute();
            ResultSet res = statement.getResultSet();
            if (res.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public List<Integer> getLessonProgress(int courseId, int studentId) {
        String sql = """
                     select lp.[lesson_id]
                     from [lesson_progress] as lp
                     join [course_content] as cc on lp.[lesson_id] = cc.[id]
                     where cc.[course_id] = ? and lp.[student_id] = ?;
                     """;
        List<Integer> list = new ArrayList<>();
        try (PreparedStatement statement = DBContext.getConnection().prepareStatement(sql)) {
            statement.setInt(1, courseId);
            statement.setInt(2, studentId);
            statement.execute();
            ResultSet res = statement.getResultSet();
            while (res.next()) {
                int lessonId = res.getInt("lesson_id");
                list.add(lessonId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<QuizResult> getQuizResult(int courseId, int studentId) {
        String sql = """
                     select qr.*
                     from [quiz_result] as qr
                     join [course_content] as cc on qr.[quiz_id] = cc.[id]
                     where cc.[course_id] = ? and qr.[student_id] = ?;
                     """;
        List<QuizResult> list = new ArrayList<>();
        try (PreparedStatement statement = DBContext.getConnection().prepareStatement(sql)) {
            statement.setInt(1, courseId);
            statement.setInt(2, studentId);
            statement.execute();
            ResultSet res = statement.getResultSet();
            while (res.next()) {
                int quizId = res.getInt("quiz_id");
                int sid = res.getInt("student_id");
                int grade = res.getInt("grade");
                boolean passed = res.getBoolean("passed");
                list.add(new QuizResult(quizId, sid, grade, passed));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public boolean checkCourseCompleted(int courseId, int studentId) {
        String getContentCountSQL = """
                                    select 
                                        count(case when cc.[type] = 'Lesson' then 1 end) as lesson_count,
                                        count(case when cc.[type] = 'Quiz' then 1 end) as quiz_count
                                    from [course_content] as cc where cc.[course_id] = ?;
                                    """;
        String getCompletedLessonSQL = """
                                       select count(*) as completed_lesson_count
                                       from [lesson_progress] as lp
                                       join [course_content] as cc on lp.[lesson_id] = cc.[id]
                                       where cc.[course_id] = ? and lp.[student_id] = ?;
                                       """;
        String getPassedQuizSQL = """
                                  select count(*) as passed_quiz_count
                                  from [quiz_result] as qr
                                  join [course_content] as cc on qr.[quiz_id] = cc.[id]
                                  where cc.[course_id] = ? and qr.[student_id] = ? and qr.[passed] = 1;
                                  """;
        String updateEnrollStatusSQL = "update [enrollment] set [status] = 'Completed' where [course_id] = ? and [student_id] = ?;";
        try (PreparedStatement ccStmt = DBContext.getConnection().prepareStatement(getContentCountSQL);
             PreparedStatement clStmt = DBContext.getConnection().prepareStatement(getCompletedLessonSQL);
             PreparedStatement pqStmt = DBContext.getConnection().prepareStatement(getPassedQuizSQL);
             PreparedStatement esStmt = DBContext.getConnection().prepareStatement(updateEnrollStatusSQL)) {
            ccStmt.setInt(1, courseId);
            ccStmt.execute();
            ResultSet res = ccStmt.getResultSet();
            int lessonCount = -1;
            int quizCount = -1;
            if (res.next()) {
                lessonCount = res.getInt("lesson_count");
                quizCount = res.getInt("quiz_count");
            }
            
            clStmt.setInt(1, courseId);
            clStmt.setInt(2, studentId);
            clStmt.execute();
            res = clStmt.getResultSet();
            int completedLessonCount = 0;
            if (res.next()) {
                completedLessonCount = res.getInt("completed_lesson_count");
            }
            
            pqStmt.setInt(1, courseId);
            pqStmt.setInt(2, studentId);
            pqStmt.execute();
            res = pqStmt.getResultSet();
            int passedQuizCount = 0;
            if (res.next()) {
                passedQuizCount = res.getInt("passed_quiz_count");
            }
            
            boolean courseComplete = completedLessonCount == lessonCount && passedQuizCount == quizCount;
            if (courseComplete) {
                esStmt.setInt(1, courseId);
                esStmt.setInt(2, studentId);
                if (esStmt.executeUpdate() > 0) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
