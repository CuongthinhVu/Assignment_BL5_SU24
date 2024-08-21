package data_access;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.CourseReport;

public class ReportDataAccess {
    private static ReportDataAccess INSTANCE;
    
    private ReportDataAccess() {
    }
    
    public static ReportDataAccess getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ReportDataAccess();
        }
        return INSTANCE;
    }
    
    public void userPurchaseCourse(int courseId, int profit) {
        String sql = "update [course_report] set [purchase_count] = [purchase_count] + 1, [profit] = [profit] + ? where [course_id] = ?;";
        try (PreparedStatement statement = DBContext.getConnection().prepareStatement(sql)) {
            statement.setInt(1, profit);
            statement.setInt(2, courseId);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void userCompleteCourse(int courseId) {
        String sql = "update [course_report] set [completed_count] = [completed_count] + 1 where [course_id] = ?;";
        try (PreparedStatement statement = DBContext.getConnection().prepareStatement(sql)) {
            statement.setInt(1, courseId);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<CourseReport> getAllReports() {
        String sql = "select * from [course_report];";
        List<CourseReport> list = new ArrayList<>();
        try (PreparedStatement statement = DBContext.getConnection().prepareStatement(sql)) {
            statement.execute();
            ResultSet res = statement.getResultSet();
            while (res.next()) {
                int id = res.getInt("id");
                int courseId = res.getInt("course_id");
                int purchaseCnt = res.getInt("purchase_count");
                int completedCnt = res.getInt("completed_count");
                int profit = res.getInt("profit");
                list.add(new CourseReport(id, courseId, purchaseCnt, completedCnt, profit));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public int getTotalProfit() {
        String sql = "select sum([profit]) as total_profit from [course_report];";
        int total = 0;
        try (PreparedStatement statement = DBContext.getConnection().prepareStatement(sql)) {
            statement.execute();
            ResultSet res = statement.getResultSet();
            while (res.next()) {
                total = res.getInt("total_profit");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }
}
