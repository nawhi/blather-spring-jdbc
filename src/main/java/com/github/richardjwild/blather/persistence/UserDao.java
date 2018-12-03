package com.github.richardjwild.blather.persistence;

import com.github.richardjwild.blather.user.User;

import java.sql.*;

public class UserDao {
    public void saveUser(String name) {
        Connection conn = null;
        PreparedStatement statement ;
        try {
            conn = createConnection();
            statement = conn.prepareStatement("INSERT INTO users VALUES (?)");
            statement.setString(1, name);
            statement.execute();
        } catch (SQLException e) {
            System.err.println("Connection failed: "+ e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/blather" +
                "?user=root&password=password");
    }

    private void closeConnection(Connection conn) {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
       User user = new UserDao().findUser("testuser");
       System.out.println("Username: " + user.name());
    }

    public User findUser(String name) {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet results = null;
        try {
            conn = createConnection();
            statement = conn.prepareStatement("SELECT * FROM users WHERE name = ?");
            statement.setString(1, name);
            results = statement.executeQuery();
            if(results.next()) return new User(results.getString(1));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }
        return null;
    }
}
