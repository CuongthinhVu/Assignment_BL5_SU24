/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data_access;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBContext {

    /**
     * main
     *
     * @author viettuts.vn
     * @param args
     */
    public static void main(String args[]) {
        try {
            // connnect to database 'testdb'
            Connection conn = getConnection();
            // close connection
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public static Connection getConnection() {

        Connection conn = null;
        String url = "jdbc:sqlserver://localhost:1433;databaseName=AssBl5Su24";
        String USER_NAME = "sa";
        String PASSWORD = "Thinhkobe0612";

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(url, USER_NAME, PASSWORD);

        } catch (Exception ex) {
            System.out.println("connect failure!");
            ex.printStackTrace();
        }
        return conn;
    }
}
