package com.github.richardjwild.blather.persistence;

import com.github.richardjwild.blather.message.Message;
import com.github.richardjwild.blather.message.MessageRepository;
import com.github.richardjwild.blather.user.User;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertTrue;

public class MySqlMessageRepositoryShould {

    @Test
    public void return_empty_collection_when_no_messages_posted_to_recipient() {
        MessageRepository repository = new MySqlMessageRepository();
        List<Message> actualMessages = repository.allMessagesPostedTo(new User("user1"))
                                                 .collect(Collectors.toList());
        assertTrue(actualMessages.isEmpty());
    }
}
