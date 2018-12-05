package com.github.richardjwild.blather.persistence;

import com.github.richardjwild.blather.user.User;

import java.sql.SQLException;
import java.util.Set;

public class FollowersDaoJdbcImpl implements FollowersDao {
    @Override
    public void saveFollowees(String follower, Set<User> followees) {

    }

    @Override
    public Set<String> getFollowees(String follower) {
        return null;
    }
}
