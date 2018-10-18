package pl.bronowicki.twitter;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(BlockJUnit4ClassRunner.class)
public class UserMessagesTest {

    private static final String USER_ID_1 = "1";
    private static final String USER_ID_2 = "2";
    private static final Message MESSAGE_1 = new Message(USER_ID_1, "text1");
    private static final Message MESSAGE_2 = new Message(USER_ID_2, "text2");
    private static final Message MESSAGE_3 = new Message(USER_ID_1, "text3");
    private static final Message MESSAGE_4 = new Message(USER_ID_2, "text4");

    private TweetService tweetService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldAddNewMessage() {
        tweetService = new TweetService();

        Message message = tweetService.addMessage(MESSAGE_1);

        assertThat(message).isEqualTo(MESSAGE_1);
    }

    @Test
    public void shouldAddNewMessagesForGivenUserId() {
        tweetService = new TweetService();

        Message messageOfUser1 = tweetService.addMessage(MESSAGE_1);
        Message messageOfUser2 = tweetService.addMessage(MESSAGE_2);

        assertThat(messageOfUser1).isEqualTo(MESSAGE_1);
        assertThat(messageOfUser2).isEqualTo(MESSAGE_2);
    }

    @Test
    public void shouldListMessagesInReverseChronologicalOrderForUser() {
        tweetService = new TweetService();

        Message message1 = tweetService.addMessage(MESSAGE_1);
        Message message2 = tweetService.addMessage(MESSAGE_3);

        List<Message> messages = new ArrayList<>(asList(message2, message1));
        List<Message> expectedMessages = new ArrayList<>(asList(MESSAGE_3, MESSAGE_1));
        assertThat(messages).isEqualTo(expectedMessages);
    }

    @Test
    public void shouldListMessagesInReverseChronologicalOrderForAllUsers() {
        tweetService = new TweetService();

        Message message1 = tweetService.addMessage(MESSAGE_1);
        Message message2 = tweetService.addMessage(MESSAGE_2);
        Message message3 = tweetService.addMessage(MESSAGE_3);
        Message message4 = tweetService.addMessage(MESSAGE_4);

        List<Message> messagesOfUser1 = new ArrayList<>(asList(message3, message1));
        List<Message> expectedMessagesOfUser1 = new ArrayList<>(asList(MESSAGE_3, MESSAGE_1));
        assertThat(messagesOfUser1).isEqualTo(expectedMessagesOfUser1);

        List<Message> messagesOfUser2 = new ArrayList<>(asList(message4, message2));
        List<Message> expectedMessagesOfUser2 = new ArrayList<>(asList(MESSAGE_4, MESSAGE_2));
        assertThat(messagesOfUser2).isEqualTo(expectedMessagesOfUser2);
    }
}