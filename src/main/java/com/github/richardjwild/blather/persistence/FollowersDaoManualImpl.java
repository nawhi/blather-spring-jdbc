package com.github.richardjwild.blather.persistence;

import com.github.richardjwild.blather.user.User;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class FollowersDaoManualImpl implements FollowersDao {
    private Connection connection;

    public FollowersDaoManualImpl(Connection connection) {

        this.connection = connection;
    }

    @Override
    public void saveFollowees(String follower, Set<User> followees)  {
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

    @Override
    public void deleteFollowees(String follower) throws SQLException {
        PreparedStatement statement;
        statement = connection.prepareStatement("DELETE FROM followers WHERE follower = ?");
        statement.setString(1, follower);
        statement.execute();
    }

    @Override
    public void addFollowees(String follower, String followee) throws SQLException {
        PreparedStatement statement;
        statement = connection.prepareStatement("INSERT INTO followers VALUES (?, ?)");
        statement.setString(1, follower);
        statement.setString(2, followee);
        statement.execute();
    }

    @Override
    public Set<String> getFollowees(String follower) {
        PreparedStatement statement = null;
        ResultSet results = null;
        Set<String> followees = new HashSet<>();

        try {
            statement = connection.prepareStatement(
                    "SELECT followee FROM followers WHERE follower = ?");
            statement.setString(1, follower);
            results = statement.executeQuery();
            while (results.next())
                followees.add(results.getString(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return followees;
    }
}
