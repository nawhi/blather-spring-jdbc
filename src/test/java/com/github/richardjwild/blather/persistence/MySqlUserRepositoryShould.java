package com.github.richardjwild.blather.persistence;

import com.github.richardjwild.blather.user.User;
import com.github.richardjwild.blather.user.UserRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;



import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    public void tell_dao_to_save_user() {
        String userName = "will_be_found";
        User expectedUser = new User(userName);
        userRepository.save(expectedUser);

        verify(userDao).saveUser(expectedUser.name());
    }

    @Test
    public void retrieve_user_when_it_exists() {
        String userName = "will_be_found";
        User expectedUser = new User(userName);
        when(userDao.findUser(userName)).thenReturn(expectedUser);

        userRepository.find(expectedUser.name());

        verify(userDao).findUser(expectedUser.name());
    }
}
