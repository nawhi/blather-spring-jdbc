package com.github.richardjwild.blather.persistence;

import com.github.richardjwild.blather.user.User;

import java.sql.*;

public class UserDaoManualImpl implements UserDao {
    private Connection connection;

    public UserDaoManualImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void saveUser(String name) {
        PreparedStatement statement ;
        try {
            statement = connection.prepareStatement("INSERT INTO users VALUES (?)");
            statement.setString(1, name);
            statement.execute();
        } catch (SQLException e) {
            System.err.println("Saveuser failed: "+ e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public String findUser(String name) {
        PreparedStatement statement = null;
        ResultSet results = null;
        try {
            statement = connection.prepareStatement("SELECT * FROM users WHERE name = ?");
            statement.setString(1, name);
            results = statement.executeQuery();
            if(results.next()) return results.getString(1);
        } catch (SQLException e) {
            System.err.println("Finduser failed: "+ e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
