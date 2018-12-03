package com.github.richardjwild.blather.persistence;

import com.github.richardjwild.blather.user.User;
import com.github.richardjwild.blather.user.UserRepository;

import java.util.Optional;

public class MySqlUserRepository implements UserRepository {
    private final UserDao userDao;
    private final FollowersDao followersDao;

    public MySqlUserRepository(UserDao userDao, FollowersDao followersDao) {
        this.userDao = userDao;
        this.followersDao = followersDao;
    }

    @Override
    public Optional<User> find(String name) {
        return Optional.ofNullable(userDao.findUser(name));
    }

    @Override
    public void save(User user) {
        userDao.saveUser(user.name());
        followersDao.saveFollowees(user.name(), user.followees());
    }
}
