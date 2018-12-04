package com.github.richardjwild.blather;

import com.github.richardjwild.blather.application.Application;
import com.github.richardjwild.blather.application.ApplicationBuilder;
import com.github.richardjwild.blather.io.ConsoleInput;
import com.github.richardjwild.blather.io.ConsoleOutput;
import com.github.richardjwild.blather.persistence.FollowersDao;
import com.github.richardjwild.blather.persistence.MySqlUserRepository;
import com.github.richardjwild.blather.persistence.UserDao;
import com.github.richardjwild.blather.time.SystemClock;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Blather {

    public static void main(String[] args) {
        Connection connection = newMySQLConnection();
        Application application = ApplicationBuilder.build(new ConsoleInput(),
                new ConsoleOutput(),
                new SystemClock(),
                new MySqlUserRepository(new UserDao(connection), new FollowersDao(connection)));
        application.run();
        closeConnection(connection);
    }

    private static void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not close SQL connection: " + e);
        }
    }

    private static Connection newMySQLConnection() {
        try {
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/blather" +
                    "?user=root&password=password");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Cannot begin SQL: " + e.getMessage());
        }
    }
}
