package pl.bronowicki.twitter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TweetControllerTest {

    private TweetController tweetController;

    @Mock
    private TweetService tweetService;

    private static final String USER_1 = "user1";
    private static final String USER_2 = "user2";
    private static final Message MESSAGE = new Message(USER_1, "text1");

    @Test
    public void shouldAddMessage() {
        when(tweetService.addMessage(MESSAGE)).thenReturn(MESSAGE);

        Message message = tweetController.newMessage(MESSAGE);

        assertThat(message).isEqualTo(MESSAGE);
    }

    @Test
    public void shouldGetUserMessages() {
        List<Message> expectedMessages = new ArrayList<>(singletonList(MESSAGE));
        when(tweetService.getMessages(USER_1)).thenReturn(expectedMessages);

        List<Message> messages = tweetController.getAllMessages(USER_1);

        assertThat(messages).isEqualTo(expectedMessages);
    }

    @Test
    public void shouldFollowUser() {
        when(tweetService.follow(USER_1, USER_2)).thenReturn(USER_1);

        String userId = tweetController.putToFollow(USER_1, USER_2);

        assertThat(userId).isEqualTo(USER_1);
    }

    @Before
    public void setUp() {
        tweetController = new TweetController(tweetService);
    }
}