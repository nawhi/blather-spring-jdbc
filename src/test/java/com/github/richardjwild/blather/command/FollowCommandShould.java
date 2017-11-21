package com.github.richardjwild.blather.command;

import com.github.richardjwild.blather.datatransfer.User;
import com.github.richardjwild.blather.datatransfer.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FollowCommandShould {

    @Mock
    private UserRepository userRepository;

    @Test
    public void add_a_user_to_the_subjects_follow_list() {
        User follower = new User("follower");
        User following = new User("following");
        when(userRepository.find("follower")).thenReturn(Optional.of(follower));
        when(userRepository.find("following")).thenReturn(Optional.of(following));
        FollowCommand followCommand = new FollowCommand("follower", "following", userRepository);

        followCommand.execute();

        verify(userRepository).save(follower);
        assertThat(follower.following()).containsOnly(following);
    }

}