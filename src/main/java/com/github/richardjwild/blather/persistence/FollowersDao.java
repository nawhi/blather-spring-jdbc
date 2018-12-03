package com.github.richardjwild.blather.persistence;

import com.github.richardjwild.blather.user.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.stream.Stream;

public class FollowersDao {
    public void saveFollowees(String follower, Stream<User> followees)  {
        Connection connection = null;
        try {
            connection = createConnection();
            connection.setAutoCommit(false);
            deleteFollowees(follower, connection);

            Connection finalConn = connection;
            followees.forEach(followee -> {
                try {
                    addFollowees(follower, followee.name(), finalConn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            connection.commit();
        } catch (SQLException e) {
            System.err.println("Connection failed: " + e.getMessage());
        } finally {
            try {
                connection.setAutoCommit(true);
                closeConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void deleteFollowees(String follower, Connection connection) throws SQLException {
        PreparedStatement statement;
        statement = connection.prepareStatement("DELETE FROM FOLLOWERS WHERE follower = ?");
        statement.setString(1, follower);
        statement.execute();
    }

    private void addFollowees(String follower, String followee, Connection connection) throws SQLException {
        PreparedStatement statement;
        statement = connection.prepareStatement("INSERT INTO followers VALUES (?, ?)");
        statement.setString(1, follower);
        statement.setString(2, followee);
        statement.execute();
    }

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/blather" +
                "?user=root&password=password");
    }

    private void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException {
        FollowersDao followersDao = new FollowersDao();
        User sarah = new User("sarah");

        followersDao.saveFollowees("Alice", Stream.of(sarah));
    }
}
