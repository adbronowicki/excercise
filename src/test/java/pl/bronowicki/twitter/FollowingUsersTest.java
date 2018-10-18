package pl.bronowicki.twitter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.time.Month;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDateTime.of;
import static java.time.ZoneId.systemDefault;
import static java.time.ZonedDateTime.of;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static pl.bronowicki.twitter.TimeService.fixedTimeAt;

@RunWith(BlockJUnit4ClassRunner.class)
public class FollowingUsersTest {

    private static final String TEXT_1 = "text1";
    private static final String TEXT_2 = "text2";
    private static final String USER_1 = "1";
    private static final String USER_2 = "2";
    private static final String USER_3 = "3";
    private static final String USER_4 = "4";
    private static final Message MESSAGE_1 = new Message(USER_1, TEXT_1);
    private static final Message MESSAGE_2 = new Message(USER_2, TEXT_2);

    private TweetService tweetService;

    @Test
    public void shouldFollowAnotherUserNotReciprocally() {
        tweetService = new TweetService();

        tweetService.addMessage(MESSAGE_1);
        tweetService.addMessage(MESSAGE_2);
        tweetService.follow(USER_1, USER_2);

        List<String> user1Followed = tweetService.getFollowed(USER_1);
        assertThat(user1Followed).isEqualTo(new ArrayList<>(singletonList(USER_2)));

        List<String> user2Followed = tweetService.getFollowed(USER_2);
        assertThat(user2Followed).isEqualTo(new ArrayList<>(emptyList()));
    }

    @Test
    public void shouldNotFollowAnyUserWhenNotRequested() {
        tweetService = new TweetService();

        tweetService.addMessage(MESSAGE_1);
        tweetService.addMessage(MESSAGE_2);

        List<String> user1Followed = tweetService.getFollowed(USER_1);
        assertThat(user1Followed).isEqualTo(new ArrayList<>(emptyList()));

        List<String> user2Followed = tweetService.getFollowed(USER_2);
        assertThat(user2Followed).isEqualTo(new ArrayList<>(emptyList()));
    }

    @Test
    public void shouldNotAffectUserWallWhenFollowUserTwice() {
        tweetService = new TweetService();

        tweetService.addMessage(MESSAGE_1);
        tweetService.addMessage(MESSAGE_2);
        tweetService.follow(USER_1, USER_2);
        tweetService.follow(USER_1, USER_2);

        List<String> user1Followed = tweetService.getFollowed(USER_1);
        assertThat(user1Followed).isEqualTo(new ArrayList<>(singletonList(USER_2)));
    }

    @Test
    public void shouldGetMessagesOfFollowedUsers() {
        tweetService = new TweetService();

        fixedTimeAt(fixedZonedDateTime(1));
        Message message1 = new Message(USER_1, "text1");

        fixedTimeAt(fixedZonedDateTime(1));
        Message message2 = new Message(USER_2, "text2");

        fixedTimeAt(fixedZonedDateTime(2));
        Message message3 = new Message(USER_3, "text3");

        fixedTimeAt(fixedZonedDateTime(3));
        Message message4 = new Message(USER_4, "text4");

        fixedTimeAt(fixedZonedDateTime(4));
        Message message5 = new Message(USER_1, "text5");

        fixedTimeAt(fixedZonedDateTime(5));
        Message message6 = new Message(USER_3, "text6");

        fixedTimeAt(fixedZonedDateTime(6));
        Message message7 = new Message(USER_2, "text7");

        tweetService.addMessage(message1);
        tweetService.addMessage(message2);
        tweetService.addMessage(message3);
        tweetService.addMessage(message4);
        tweetService.addMessage(message5);
        tweetService.addMessage(message6);
        tweetService.addMessage(message7);

        tweetService.follow(USER_1, USER_2);
        tweetService.follow(USER_1, USER_3);

        List<Message> user1Messages = tweetService.getAllMessages(USER_1);
        assertThat(user1Messages)
                .isEqualTo(new ArrayList<>(asList(message7, message6, message5, message3, message2, message1)));

        List<String> usersWithoutFollowed = new ArrayList<>(asList(USER_2, USER_3, USER_4));
        usersWithoutFollowed.forEach(user -> {
            List<String> userFollowed = tweetService.getFollowed(user);
            assertThat(userFollowed).isEqualTo(new ArrayList<>(emptyList()));
        });
    }

    private ZonedDateTime fixedZonedDateTime(int seconds) {
        return of(of(2018, Month.OCTOBER, 15, 9, 0, seconds), systemDefault());
    }
}