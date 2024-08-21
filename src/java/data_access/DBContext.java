package data_access;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBContext {
    private static Connection conn = null;
    public static Connection getConnection() {
        if (conn != null) return conn;
        String url = "jdbc:sqlserver://localhost:1433;databaseName=AssBL5Su24";
        String USER_NAME = "sa";
        String PASSWORD = "Thinhkobe0612";
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(url, USER_NAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
        return conn;
    }
}
