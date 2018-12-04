package com.github.richardjwild.blather.persistence;

import java.util.List;

public class MessageDao {
    public List<MessageDto> getMessagesFor(String userName) {
        return null;
    }

    public static void main(String[] args) {
        MessageDao messageDao = new MessageDao();
        List<MessageDto> messages = messageDao.getMessagesFor("testuser");
        messages.forEach(System.out::println);
    }
}
