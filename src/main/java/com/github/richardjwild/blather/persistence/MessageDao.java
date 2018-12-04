package com.github.richardjwild.blather.persistence;


import java.sql.*;
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
    }
}
