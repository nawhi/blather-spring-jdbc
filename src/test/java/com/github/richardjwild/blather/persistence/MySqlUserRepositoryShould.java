package com.github.richardjwild.blather.persistence;

import com.github.richardjwild.blather.user.User;
import com.github.richardjwild.blather.user.UserRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;



import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class MySqlUserRepositoryShould {

    private UserRepository userRepository;
    private UserDao userDao;

    @Before
    public void setUp() {
        userDao = mock(UserDao.class);
        userRepository = new MySqlUserRepository(userDao);
    }

    @Test
    public void return_empty_when_user_not_found() {
        Optional<User> result = userRepository.find("will_not_be_found");

        assertThat(result.isPresent()).isFalse();
    }

    @Test
    public void return_stored_user_when_user_is_found() {
        String userName = "will_be_found";
        User expectedUser = new User(userName);
        userRepository.save(expectedUser);

        verify(userDao).saveUser(expectedUser.name());
    }
}
