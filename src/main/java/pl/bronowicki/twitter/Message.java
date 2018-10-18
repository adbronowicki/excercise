package pl.bronowicki.twitter;

import org.springframework.beans.factory.annotation.Autowired;

import java.time.ZonedDateTime;
import java.util.Objects;

class Message implements Comparable<Message> {

    private String userId;
    private ZonedDateTime zonedDateTime;
    private String text;

    @Autowired
    private TimeService timeService;

    public Message() {
    }

    Message(String userId, String text) {
        if (text.length() > 140) {
            throw new IllegalArgumentException("Message should be up to 140 characters");
        }
        this.text = text;
        this.userId = userId;
        this.zonedDateTime = TimeService.now();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ZonedDateTime getZonedDateTime() {
        return zonedDateTime;
    }

    public void setZonedDateTime(ZonedDateTime zonedDateTime) {
        this.zonedDateTime = zonedDateTime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public int compareTo(Message otherMessage) {
        ZonedDateTime compareQuantity = otherMessage.getZonedDateTime();
        return compareQuantity.isAfter(this.zonedDateTime) ? -1 : 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(userId, message.userId) &&
                Objects.equals(zonedDateTime, message.zonedDateTime) &&
                Objects.equals(text, message.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, zonedDateTime, text);
    }

    @Override
    public String toString() {
        return "Message{" +
                "userId='" + userId + '\'' +
                ", zonedDateTime=" + zonedDateTime +
                ", text='" + text + '\'' +
                '}';
    }
}