package com.github.richardjwild.blather.persistence;

import com.github.richardjwild.blather.user.User;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FollowersDaoJdbcImpl implements FollowersDao {
    private JdbcTemplate jdbcTemplate;

    public FollowersDaoJdbcImpl(JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveFollowees(String follower, Set<User> followees) {

    }

    @Override
    public Set<String> getFollowees(String follower) {
        List<String> results = jdbcTemplate.query(
                "SELECT followee FROM followers WHERE follower = ?",
                new Object[] { follower },
                (rs, rowNum) -> rs.getString(1)
        );
        return new HashSet<>(results);
    }

    public static void main(String[] args) {
        var dataSource = new MysqlDataSource();
        dataSource.setUser("root");
        dataSource.setPassword("password");
        dataSource.setDatabaseName("blather");
        dataSource.setPort(3306);
        
        var dao = new FollowersDaoJdbcImpl(new JdbcTemplate(dataSource));
        dao.getFollowees("toni").stream().forEach(System.out::println);
        
    }
}
