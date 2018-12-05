package com.github.richardjwild.blather.persistence;

import com.github.richardjwild.blather.user.User;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FollowersDaoJdbcImpl implements FollowersDao {
    private JdbcTemplate jdbcTemplate;

    public FollowersDaoJdbcImpl(JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveFollowees(String follower, Set<User> followees) {
        Set<String> currentFollowees = getFollowees(follower);
        List<User> followeesToBeSaved = followees.stream()
                .filter(f -> !currentFollowees.contains(f))
                .collect(Collectors.toList());

        final String sql = "INSERT INTO followers VALUES (?, ?)";
        jdbcTemplate.batchUpdate(
                sql,
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, follower);
                        ps.setString(2, followeesToBeSaved.get(i).name());
                    }

                    @Override
                    public int getBatchSize() {
                        return followeesToBeSaved.size();
                    }
        });
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

        dao.saveFollowees("toni", Set.of(new User("alice"), new User("bob")));

        dao.getFollowees("toni").stream().forEach(System.out::println);
        // nick, alice, bob
        
    }
}
