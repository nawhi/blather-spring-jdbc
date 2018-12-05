package com.github.richardjwild.blather;

import com.github.richardjwild.blather.application.Application;
import com.github.richardjwild.blather.application.ApplicationBuilder;
import com.github.richardjwild.blather.io.ConsoleInput;
import com.github.richardjwild.blather.io.ConsoleOutput;
import com.github.richardjwild.blather.persistence.*;
import com.github.richardjwild.blather.time.SystemClock;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class Blather {

    public static void main(String[] args) {
        DataSource dataSource = newMySqlDataSource();
        Connection connection = getConnection(dataSource);
        Application application = ApplicationBuilder.build(new ConsoleInput(),
                new ConsoleOutput(),
                new SystemClock(),
                new MySqlUserRepository(new UserDaoJdbcImpl(new JdbcTemplate(dataSource)), new FollowersDaoManualImpl(connection)),
                new MySqlMessageRepository(new MessageDao(connection)));
        application.run();
        closeConnection(connection);
    }

    private static Connection getConnection(DataSource dataSource) {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Could not get connection: " + e);
        }
    }

    private static void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not close SQL connection: " + e);
        }
    }

    private static DataSource newMySqlDataSource() {
        var dataSource = new MysqlDataSource();
        dataSource.setUser("root");
        dataSource.setPassword("password");
        dataSource.setDatabaseName("blather");
        dataSource.setPort(3306);
        return dataSource;
    }
}
