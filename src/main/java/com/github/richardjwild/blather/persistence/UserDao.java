package com.github.richardjwild.blather.persistence;

import com.github.richardjwild.blather.user.User;

import java.sql.*;

public class UserDao {
    public void saveUser(String name) {
        Connection conn = null;
        PreparedStatement statement ;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/blather" +
                    "?user=root&password=password");
            statement = conn.prepareStatement("INSERT INTO users VALUES (?)");
            statement.setString(1, name);
            statement.execute();
        } catch (SQLException e) {
            System.err.println("Connection failed: "+ e.getMessage());
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new UserDao().saveUser("testuser");
    }

    public User findUser(String name) {
      return null;
    }
}
