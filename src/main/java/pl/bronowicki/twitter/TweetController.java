package pl.bronowicki.twitter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
class TweetController {

    private final TweetService tweetService;

    TweetController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @PostMapping(value = "/messages",
            consumes = "application/json",
            produces = "application/json")
    @ResponseStatus(CREATED)
    @ResponseBody
    Message newMessage(Message newMessage) {
        return tweetService.addMessage(newMessage);
    }

    @GetMapping("/messages/{userId}")
    @ResponseBody
    List<Message> getAllMessages(@PathVariable String userId) {
        return tweetService.getMessages(userId);
    }

    @PutMapping("/messages/{userId}/follow/{userIdToFollow}")
    @ResponseBody
    String putToFollow(@PathVariable String userId, @PathVariable String userIdToFollow) {
        return tweetService.follow(userId, userIdToFollow);
    }
}