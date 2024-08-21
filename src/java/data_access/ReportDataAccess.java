package data_access;

import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
