package pl.bronowicki.twitter;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(BlockJUnit4ClassRunner.class)
public class MessageTest {

    private static final String USER_ID = "1";
    private static final String TEXT_WITH_140_CHARS = "1234567890qwertyuiopasdfghjklzxcvbnm1234567890qwertyuiopasd" +
            "fghjklzxcvbnm1234567890qwertyuiopasdfghjklzxcvbnm1234567890qwertyuiopasdfghjklzxc";
    private static final String TEXT_WITH_141_CHARS = TEXT_WITH_140_CHARS + "v";

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldAddNewMessageWithLengthUpTo140Chars() {
        Message message = new Message(USER_ID, TEXT_WITH_140_CHARS);

        assertThat(message.getText()).isEqualTo(TEXT_WITH_140_CHARS);
    }

    @Test
    public void shouldNotCreateMessageWithLengthMoreThan140Chars() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Message should be up to 140 characters");

        new Message(USER_ID, TEXT_WITH_141_CHARS);
    }
}