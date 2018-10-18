package pl.bronowicki.twitter;

import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;

@Service
class TweetService {

    private final Map<String, UserWall> usersMessagesMap = new HashMap<>();

    Message addMessage(Message message) {
        String userId = message.getUserId();
        UserWall userWall = usersMessagesMap.get(userId);
        if (userWall == null) {
            userWall = new UserWall(userId);
        }
        userWall.add(message);
        usersMessagesMap.put(userId, userWall);

        return message;
    }

    List<Message> getMessages(String userId) {
        UserWall userWall = usersMessagesMap.get(userId);
        List<Message> messages = userWall.getMessages();
        messages.sort((m1, m2) -> {
            ZonedDateTime compareQuantity = m2.getZonedDateTime();
            return compareQuantity.isAfter(m1.getZonedDateTime()) ? 1 : -1;
        });
        return messages;
    }

    String follow(String userId, String userIdToFollow) {
        UserWall userWall = usersMessagesMap.get(userId);
        if (userWall != null) {
            followIdempotently(userIdToFollow, userWall);
        } else {
            throw new IllegalArgumentException();
        }
        return userIdToFollow;
    }
    private void followIdempotently(String userIdToFollow, UserWall userWall) {
        if (!isAlreadyFollowing(userIdToFollow, userWall)) {
            userWall.follow(userIdToFollow);
        }
    }

    private boolean isAlreadyFollowing(String userIdToFollow, UserWall userWall) {
        return userWall.getFollowed().contains(userIdToFollow);
    }

    List<String> getFollowed(String userId) {
        UserWall userWall = usersMessagesMap.get(userId);
        return userWall.getFollowed();
    }

    List<Message> getAllMessages(String userId) {
        UserWall userWall = usersMessagesMap.get(userId);
        if (userWall == null) {
            return emptyList();
        } else {
            List<Message> userWallMessages = userWall.getMessages();
            List<String> followedUsers = userWall.getFollowed();
            followedUsers.forEach(followed -> userWallMessages.addAll(getMessages(followed)));
            userWallMessages.sort((m1, m2) -> {
                ZonedDateTime compareQuantity = m2.getZonedDateTime();
                return compareQuantity.isAfter(m1.getZonedDateTime()) ? 1 : -1;
            });
            return userWallMessages;
        }
    }
}