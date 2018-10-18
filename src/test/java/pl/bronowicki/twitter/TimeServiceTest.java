package pl.bronowicki.twitter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import static java.time.Month.OCTOBER;
import static java.time.ZoneId.systemDefault;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(BlockJUnit4ClassRunner.class)
public class TimeServiceTest {

    private final ZonedDateTime FIXED_TIME =
            ZonedDateTime.of(LocalDateTime.of(2018, OCTOBER, 15, 12, 0, 0), systemDefault());

    @Test
    public void shouldShowCurrentTime() throws InterruptedException {
        ZonedDateTime currentTime = TimeService.now();
        Thread.sleep(100);
        ZonedDateTime laterTime = TimeService.now();

        assertThat(laterTime).isAfter(currentTime);
    }

    @Test
    public void shouldShowFixedTime() {
        TimeService.fixedTimeAt(FIXED_TIME);

        assertThat(FIXED_TIME).isEqualTo(TimeService.now());
    }

    @Before
    public void setUp() {
        TimeService.reset();
    }
}