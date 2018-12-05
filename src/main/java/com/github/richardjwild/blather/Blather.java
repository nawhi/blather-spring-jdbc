package com.github.richardjwild.blather;

import com.github.richardjwild.blather.application.Application;
import com.github.richardjwild.blather.application.ApplicationBuilder;
import com.github.richardjwild.blather.io.ConsoleInput;
import com.github.richardjwild.blather.io.ConsoleOutput;
import com.github.richardjwild.blather.persistence.*;
import com.github.richardjwild.blather.time.SystemClock;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Blather {

    public static void main(String[] args) {
        Connection connection = newMySQLConnection();
        Application application = ApplicationBuilder.build(new ConsoleInput(),
                new ConsoleOutput(),
                new SystemClock(),
                new MySqlUserRepository(new UserDaoManualImpl(connection), new FollowersDao(connection)), new MySqlMessageRepository(new MessageDao(connection)));
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
            var dataSource = new MysqlDataSource();
            dataSource.setUser("root");
            dataSource.setPassword("password");
            dataSource.setDatabaseName("blather");
            dataSource.setPort(3306);
            return dataSource.getConnection();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Cannot begin SQL: " + e.getMessage());
        }
    }
}
