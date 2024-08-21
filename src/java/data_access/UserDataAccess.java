package data_access;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.User;

public class UserDataAccess {
    private static UserDataAccess INSTANCE;
    
    private UserDataAccess() {
    }
    
    public static UserDataAccess getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserDataAccess();
        }
        return INSTANCE;
    }
    
    public List<User> getAllUsers() {
        String sql = "select * from [user];";
        List<User> list = new ArrayList<>();
        try (PreparedStatement statement = DBContext.getConnection().prepareStatement(sql)) {
            statement.execute();
            ResultSet res = statement.getResultSet();
            while (res.next()) {
                int id = res.getInt("id");
                String mail = res.getString("email");
                LocalDateTime createdAt = res.getTimestamp("created_at").toLocalDateTime();
                list.add(new User(id, mail, createdAt));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public boolean register(String email, String password) {
        String sql = "insert into [user]([email], [password]) values (?, ?);";
        try (PreparedStatement statement = DBContext.getConnection().prepareStatement(sql)) {
            statement.setString(1, email);
            statement.setString(2, password);
            statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public User login(String email, String password) {
        String sql = "select * from [user] where [email] = ? and [password] = ?;";
        User user = null;
        try (PreparedStatement statement = DBContext.getConnection().prepareStatement(sql)) {
            statement.setString(1, email);
            statement.setString(2, password);
            statement.execute();
            ResultSet res = statement.getResultSet();
            while (res.next()) {
                int id = res.getInt("id");
                String mail = res.getString("email");
                java.sql.Timestamp temp = res.getTimestamp("created_at");
                LocalDateTime createdAt = null;
                if (temp != null) {
                    createdAt = temp.toLocalDateTime();
                }
                user = new User(id, mail, createdAt);
                break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public User getUserByEmail(String email) {
        String sql = "select * from [user] where [email] = ?;";
        User user = null;
        try (PreparedStatement statement = DBContext.getConnection().prepareStatement(sql)) {
            statement.setString(1, email);
            statement.execute();
            ResultSet res = statement.getResultSet();
            while (res.next()) {
                int id = res.getInt("id");
                String mail = res.getString("email");
                java.sql.Timestamp temp = res.getTimestamp("created_at");
                LocalDateTime createdAt = null;
                if (temp != null) {
                    createdAt = temp.toLocalDateTime();
                }
                user = new User(id, mail, createdAt);
                break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
    
    public void changePassword(int id, String password) {
        String sql = "update [user] set [password] = ? where [id] = ?;";
        try (PreparedStatement statement = DBContext.getConnection().prepareStatement(sql)) {
            statement.setString(1, password);
            statement.setInt(2, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void deleteAccount(int id) {
        String sql = "delete from [user] where [id] = ?;";
        try (PreparedStatement statement = DBContext.getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
