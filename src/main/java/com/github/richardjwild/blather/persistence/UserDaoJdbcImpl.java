package com.github.richardjwild.blather.persistence;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class UserDaoJdbcImpl implements UserDao {

    private JdbcTemplate jdbcTemplate;

    public UserDaoJdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveUser(String name) {
        jdbcTemplate.update("INSERT INTO users VALUES (?)", new Object[] { name });
    }

    @Override
    public String findUser(String name) {
        String userName = null;
        try {
            userName = jdbcTemplate.queryForObject(
                    "SELECT name FROM users WHERE name = ?",
                    new Object[]{name},
                    String.class
            );
        } catch (EmptyResultDataAccessException ex) {

        }
        return userName;
    }
}
