package com.github.richardjwild.blather.persistence;

import com.github.richardjwild.blather.user.User;

import java.sql.SQLException;
import java.util.Set;

public interface FollowersDao {
    void saveFollowees(String follower, Set<User> followees);

    void deleteFollowees(String follower) throws SQLException;

    void addFollowees(String follower, String followee) throws SQLException;

    Set<String> getFollowees(String follower);
}
