package com.github.richardjwild.blather.persistence;

import com.github.richardjwild.blather.message.Message;
import com.github.richardjwild.blather.message.MessageRepository;
import com.github.richardjwild.blather.user.User;

import java.util.stream.Stream;

public class MySqlMessageRepository implements MessageRepository {
    @Override
    public void postMessage(User recipient, Message message) {

    }

    @Override
    public Stream<Message> allMessagesPostedTo(User recipient) {
        return Stream.empty();
    }
}
