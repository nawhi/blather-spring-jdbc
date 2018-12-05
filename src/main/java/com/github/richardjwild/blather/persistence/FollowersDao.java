package com.github.richardjwild.blather.persistence;

import com.github.richardjwild.blather.user.User;

import java.sql.SQLException;
import java.util.Set;

public interface FollowersDao {
    void saveFollowees(String follower, Set<User> followees);

    Set<String> getFollowees(String follower);
}
