package com.github.richardjwild.blather.persistence;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class UsersDaoJdbcImpl implements UserDao {

    private JdbcTemplate jdbcTemplate;

    public UsersDaoJdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static void main(String[] args) {
        var dataSource = new MysqlDataSource();
        dataSource.setUser("root");
        dataSource.setPassword("password");
        dataSource.setDatabaseName("blather");
        dataSource.setPort(3306);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        UsersDaoJdbcImpl usersDaoJdbc = new UsersDaoJdbcImpl(jdbcTemplate);
        System.out.println(usersDaoJdbc.findUser("nick"));
    }

    @Override
    public void saveUser(String name) {

    }

    @Override
    public String findUser(String name) {
         return jdbcTemplate.queryForObject(
                 "SELECT name FROM users WHERE name = ?",
                 new Object[] { name },
                 String.class
         );
    }
}
