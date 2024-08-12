/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data_access;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import model.User;
/**
 *
 * @author LONG
 */
public class Account_DAO {
    
    public void register(String email, String password) {
        String sql = "insert into [user]([email], [password]) values (?, ?);";
        try (PreparedStatement statement = DBContext.getConnection().prepareStatement(sql)) {
            statement.setString(1, email);
            statement.setString(2, password);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
     public User login(String email, String password) {
        String sql = "select * from [user] where [email] = ?;";
        User user = null;

        try (PreparedStatement statement = DBContext.getConnection().prepareStatement(sql)) {
            statement.setString(1, email);
            
            statement.execute();
            ResultSet res = statement.getResultSet();
            String pass = null;
            while (res.next()) {
                int id = res.getInt("id");
                String mail = res.getString("email");
                pass = res.getString("password");
                LocalDateTime time = res.getTimestamp("created_at").toLocalDateTime();
                user = new User(id, email, time);
                break;
            }
            
            if(password.equals(pass)){
                return user;
            }else{
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
