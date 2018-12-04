package com.github.richardjwild.blather.persistence;

import com.github.richardjwild.blather.user.User;
import com.github.richardjwild.blather.user.UserRepository;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class MySqlUserRepository implements UserRepository {
    private final UserDao userDao;
    private final FollowersDao followersDao;

    public MySqlUserRepository(UserDao userDao, FollowersDao followersDao) {
        this.userDao = userDao;
        this.followersDao = followersDao;
    }

    @Override
    public Optional<User> find(String name) {
        String foundName = userDao.findUser(name);
        if (foundName == null)
            return Optional.empty();

        Set<String> usernamesFollowing = followersDao.getFollowees(name);
        Set<User> usersFollowing = usernamesFollowing
                    .stream()
                    .map((n) -> new User(n))
                    .collect(Collectors.toSet());
        return Optional.of(new User(foundName, usersFollowing));

    }

    @Override
    public void save(User user) {
        userDao.saveUser(user.name());
        followersDao.saveFollowees(user.name(), user.followees());
    }
}
