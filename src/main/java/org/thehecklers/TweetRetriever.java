package org.thehecklers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import twitter4j.*;
import twitter4j.auth.AccessToken;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

/**
 * Created by markheckler on 3/5/17.
 */
@Component
public class TweetRetriever {
    private Twitter twitter;

    @Value("${twitter.key}")
    private String consumerKey;

    @Value("${twitter.secret}")
    private String consumerSecret;

    @Value("${twitter.token}")
    private String accessToken;

    @Value("${twitter.token.secret}")
    private String accessTokenSecret;

    @Value("${twitter.fromuser:pivotal}")
    private String fromUser;

    @PostConstruct
    public void init() {
        this.twitter = TwitterFactory.getSingleton();
        this.twitter.setOAuthConsumer(this.consumerKey, this.consumerSecret);
        this.twitter.setOAuthAccessToken(new AccessToken(this.accessToken, this.accessTokenSecret));
    }

    private Tweet createTweet(Status status) {
        return new Tweet(status.getText(),
            status.getInReplyToScreenName(),
            status.getQuotedStatus() == null ? null : status.getQuotedStatus().getText());
    }

    public Tweet getRandomTweet() {
        return getRandomTweet(this.fromUser);
    }

    public Tweet getRandomTweet(String fromUser) {
        String user = fromUser;

        if (Stream.of("mk heck", "mk hack", "nk heck", "nk hack", "mk hat", "k hat", "nk hat", "mk hacked", "mk pack",
                "mk packed", "m. k. heck", "m. k. hack", "n. k. heck", "n. k. hack", "m. k. hat", "k. hat", "n. k. hat",
                "m. k. hacked", "m. k. pack", "m. k. packed", "m. k. hat", "hack", "heck", "hat")
                .anyMatch(s -> s.equalsIgnoreCase(fromUser))) {
            user = "mkheck";
        } else if (Stream.of("little idea", "lil idea", "idea")
                .anyMatch(s -> s.equalsIgnoreCase(fromUser))) {
            user = "littleidea";
        } else if ("pivotal cf".equalsIgnoreCase(fromUser)) {
            user = "pivotalcf";
        }

        Query query = new Query("from:" + user);
        try {
            QueryResult result = this.twitter.search(query);
            List<Status> tweets = result.getTweets();

            System.out.println(">>> Twitter user: " + user);
            //tweets.stream().map(this::createTweet).forEach(System.out::println);

            return createTweet(tweets.get(new Random().nextInt(tweets.size())));
        } catch (TwitterException e) {
            e.printStackTrace();
        }

        return new Tweet("Tweet fail!", null, null);
    }
}
