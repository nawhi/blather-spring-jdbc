package com.github.richardjwild.blather.persistence;


import com.github.richardjwild.blather.message.Message;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class MessageDao {
    private final Connection connection;

    public MessageDao(Connection connection) {

        this.connection = connection;
    }

    public List<MessageDto> getMessagesFor(String userName) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<MessageDto> messages = new ArrayList<>();
        try {
            statement = connection.prepareStatement("SELECT recipient, text, post_time FROM messages WHERE recipient = ?");
            statement.setString(1, userName);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String recipient = resultSet.getString(1);
                String text = resultSet.getString(2);
                Timestamp timestamp = resultSet.getTimestamp(3);
                messages.add(new MessageDto(recipient, text, timestamp));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

    public void postMessage(MessageDto messageDto) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO messages (recipient, text, post_time) VALUES (?, ?, ?)");
            statement.setString(1, messageDto.getRecipientName());
            statement.setString(2, messageDto.getText());
            statement.setTimestamp(3, messageDto.getTimestamp());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/blather?user=root&password=password")) {
            MessageDao messageDao = new MessageDao(connection);
            messageDao.postMessage(new MessageDto("testuser", "Isn't it nice weather?", Timestamp.from(Instant.ofEpochSecond(8000))));
            List<MessageDto> messages = messageDao.getMessagesFor("testuser");
            messages.forEach(System.out::println);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
