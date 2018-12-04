package com.github.richardjwild.blather.persistence;

import com.github.richardjwild.blather.message.Message;
import com.github.richardjwild.blather.message.MessageRepository;
import com.github.richardjwild.blather.user.User;

import java.util.stream.Stream;

public class MySqlMessageRepository implements MessageRepository {
    private MessageDao messageDao;

    public MySqlMessageRepository(MessageDao messageDao) {
        this.messageDao = messageDao;
    }

    @Override
    public void postMessage(User recipient, Message message) {

    }

    @Override
    public Stream<Message> allMessagesPostedTo(User recipient) {
        var messages = messageDao.getMessagesFor(recipient.name());
        return Stream.empty();
    }
}
