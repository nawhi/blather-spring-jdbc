package com.github.richardjwild.blather.command;

import com.github.richardjwild.blather.datatransfer.Message;
import com.github.richardjwild.blather.datatransfer.MessageRepository;
import com.github.richardjwild.blather.datatransfer.User;
import com.github.richardjwild.blather.io.Output;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.Instant;

import static java.util.Arrays.asList;
import static org.mockito.Matchers.startsWith;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReadCommandShould {

    private static final User SUBJECT = new User();

    @Mock
    private Output output;

    @Mock
    private MessageRepository messageRepository;

    @Test
    public void print_all_messages_posted_to_a_specified_user() {
        String messageText1 = "message one";
        String messageText2 = "message two";
        Message message1 = new Message(SUBJECT, messageText1, Instant.now());
        Message message2 = new Message(SUBJECT, messageText2, Instant.now());
        when(messageRepository.allMessagesPostedTo(SUBJECT)).thenReturn(asList(message1, message2));
        ReadCommand command = new ReadCommand(SUBJECT, messageRepository, output);

        command.execute();

        InOrder inOrder = inOrder(output);
        inOrder.verify(output).writeLine(startsWith(messageText1));
        inOrder.verify(output).writeLine(startsWith(messageText2));
    }

}