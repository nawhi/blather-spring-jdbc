package com.github.richardjwild.blather.persistence;

import com.github.richardjwild.blather.message.Message;
import com.github.richardjwild.blather.message.MessageRepository;
import com.github.richardjwild.blather.user.User;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class MySqlMessageRepositoryShould {

    private MessageDao messageDao;
    private MessageRepository repository;

    @Before
    public void setUp() {
        messageDao = mock(MessageDao.class);
        repository = new MySqlMessageRepository(messageDao);

    }

    @Test
    public void return_empty_collection_when_no_messages_posted_to_recipient() {
        List<Message> actualMessages = repository.allMessagesPostedTo(new User("user1"))
                                                 .collect(Collectors.toList());
        assertTrue(actualMessages.isEmpty());
    }

    @Test
    public void ask_dao_to_retrieve_messages() {
        String userName = "user1";
        User user = new User(userName);

        repository.allMessagesPostedTo(user);

        verify(messageDao).getMessagesFor(userName);
    }
}
