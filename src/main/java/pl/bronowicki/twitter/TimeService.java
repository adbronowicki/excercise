package pl.bronowicki.twitter;

import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
class TimeService {

    private static ZonedDateTime zonedDateTime;

    static ZonedDateTime now() {
        return (zonedDateTime != null) ? zonedDateTime : ZonedDateTime.now();
    }

    static void fixedTimeAt(ZonedDateTime zonedDateTime) {
        TimeService.zonedDateTime = zonedDateTime;
    }

    static void reset() {
        zonedDateTime = null;
    }
}