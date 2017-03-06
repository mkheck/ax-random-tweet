package org.thehecklers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by markheckler on 3/5/17.
 */
@RestController
public class TweetController {
    private TweetRetriever tweetRetriever;

    public TweetController(TweetRetriever tweetRetriever) {
        this.tweetRetriever = tweetRetriever;
    }

    @GetMapping("/randomtweet")
    public Tweet getTweet() {
        return this.tweetRetriever.getRandomTweet();
    }
}
