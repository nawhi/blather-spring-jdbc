package com.github.richardjwild.blather.persistence;

import java.sql.Timestamp;
import java.util.Objects;

public class MessageDto {
    private final String recipientName;
    private final String text;
    private final Timestamp timestamp;

    public MessageDto(String recipientName, String text, Timestamp timestamp) {

        this.recipientName = recipientName;
        this.text = text;
        this.timestamp = timestamp;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public String getText() {
        return text;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageDto that = (MessageDto) o;
        return Objects.equals(recipientName, that.recipientName) &&
                Objects.equals(text, that.text) &&
                Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipientName, text, timestamp);
    }
}
