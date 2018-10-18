package pl.bronowicki.twitter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class UserWall {

    private final String userId;
    private final List<Message> messages;
    private final List<String> followed;

    UserWall(String userId) {
        this.userId = userId;
        this.messages = new ArrayList<>();
        this.followed = new ArrayList<>();
    }

    void add(Message message) {
        messages.add(message);
    }

    void follow(String userIdToFollow) {
        followed.add(userIdToFollow);
    }

    List<Message> getMessages() {
        return messages;
    }

    List<String> getFollowed() {
        return followed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserWall userWall = (UserWall) o;
        return Objects.equals(userId, userWall.userId) &&
                Objects.equals(messages, userWall.messages) &&
                Objects.equals(followed, userWall.followed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, messages, followed);
    }

    @Override
    public String toString() {
        return "UserWall{" +
                "userId='" + userId + '\'' +
                ", messages=" + messages +
                ", followed=" + followed +
                '}';
    }
}