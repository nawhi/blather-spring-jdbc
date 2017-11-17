package com.github.richardjwild.blather.command;

import com.github.richardjwild.blather.application.Controller;
import com.github.richardjwild.blather.datatransfer.MessageRepository;
import com.github.richardjwild.blather.datatransfer.User;
import com.github.richardjwild.blather.io.Output;
import com.github.richardjwild.blather.time.Clock;
import com.github.richardjwild.blather.time.TimestampFormatter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class CommandFactoryShould {

    private static final User RECIPIENT = new User(), SUBJECT = new User(), FOLLOWER = new User();
    private static final String MESSAGE = "a message";

    private CommandFactory commandFactory;

    @Mock
    private Controller controller;

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private Clock clock;

    @Mock
    private TimestampFormatter timestampFormatter;

    @Mock
    private Output output;

    @Before
    public void initialize() {
        commandFactory = new CommandFactory(controller, messageRepository, clock, timestampFormatter, output);
    }

    @Test
    public void make_a_quit_command() {
        QuitCommand expectedCommand = new QuitCommand(controller);

        Command command = commandFactory.makeQuitCommand();

        assertThat(command).isEqualTo(expectedCommand);
    }

    @Test
    public void make_a_post_command() {
        PostCommand expectedCommand = new PostCommand(RECIPIENT, MESSAGE, messageRepository, clock);

        Command command = commandFactory.makePostCommand(RECIPIENT, MESSAGE);

        assertThat(command).isEqualTo(expectedCommand);
    }

    @Test
    public void make_a_read_command() {
        ReadCommand expectedCommand = new ReadCommand(SUBJECT, messageRepository, timestampFormatter, output);

        Command command = commandFactory.makeReadCommand(SUBJECT);

        assertThat(command).isEqualTo(expectedCommand);
    }

    @Test
    public void make_a_follow_command() {
        FollowCommand expectedCommand = new FollowCommand(FOLLOWER, SUBJECT);

        Command command = commandFactory.makeFollowCommand(FOLLOWER, SUBJECT);

        assertThat(command).isEqualTo(expectedCommand);
    }

    @Test
    public void make_a_wall_command() {
        WallCommand expectedCommand = new WallCommand(SUBJECT);

        Command command = commandFactory.makeWallCommand(SUBJECT);

        assertThat(command).isEqualTo(expectedCommand);
    }
}