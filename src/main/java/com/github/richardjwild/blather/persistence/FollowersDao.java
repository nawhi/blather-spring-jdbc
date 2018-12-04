package com.github.richardjwild.blather.persistence;

import com.github.richardjwild.blather.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.stream.Stream;

public class FollowersDao {
    private Connection connection;

    public FollowersDao(Connection connection) {

        this.connection = connection;
    }

    public void saveFollowees(String follower, Stream<User> followees)  {
        try {
            connection.setAutoCommit(false);
            deleteFollowees(follower);

            followees.forEach(followee -> {
                try {
                    addFollowees(follower, followee.name());
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void deleteFollowees(String follower) throws SQLException {
        PreparedStatement statement;
        statement = connection.prepareStatement("DELETE FROM followers WHERE follower = ?");
        statement.setString(1, follower);
        statement.execute();
    }

    private void addFollowees(String follower, String followee) throws SQLException {
        PreparedStatement statement;
        statement = connection.prepareStatement("INSERT INTO followers VALUES (?, ?)");
        statement.setString(1, follower);
        statement.setString(2, followee);
        statement.execute();
    }

}
