package org.thehecklers;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.*;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.SimpleCard;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by markheckler on 12/30/16.
 */
@Service
public class TweetSpeechlet implements Speechlet {
    private TweetRetriever tweetRetriever;

    @Value("${application.id}")
    private String applicationId;

    public TweetSpeechlet(TweetRetriever tweetRetriever) {
        this.tweetRetriever = tweetRetriever;
    }

    @Override
    public void onSessionStarted(SessionStartedRequest sessionStartedRequest, Session session) throws SpeechletException {

    }

    @Override
    public SpeechletResponse onLaunch(LaunchRequest launchRequest, Session session) throws SpeechletException {
        //return processCommand(Intent.builder().withName("TweetIntent").build());
        return null;
    }

    @Override
    public SpeechletResponse onIntent(IntentRequest intentRequest, Session session) throws SpeechletException {
        if (session.getApplication().getApplicationId().equalsIgnoreCase(applicationId)) {
            Intent intent = intentRequest.getIntent();
            if (intent == null) {
                throw new SpeechletException("Unrecognized intent!");
            }

            return processCommand(intent);
        } else {
            throw new SpeechletException("You are not authorized to access this skill. Cease & desist, and have a nice day.");
        }
    }

    private SpeechletResponse processCommand(Intent intent) {
        Tweet tweet;
        String cardTitle = "Random tweet";

        if (intent.getName().equalsIgnoreCase("TweetForUserIntent")) {
            tweet = this.tweetRetriever.getRandomTweet(intent.getSlot("User").getValue());
            cardTitle += " by " + intent.getSlot("User").getValue();
        } else { // TweetIntent, no Twitter user specified
            tweet = this.tweetRetriever.getRandomTweet();
        }

        SimpleCard card = new SimpleCard();
        card.setTitle(cardTitle);
        card.setContent(tweet.getText() + (tweet.getQuotedTweet() == null ? "" : ", quoted tweet: " + tweet.getQuotedTweet()));

        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(tweet.getText() + (tweet.getQuotedTweet() == null ? "" : " : " + tweet.getQuotedTweet()));

        return SpeechletResponse.newTellResponse(speech, card);
    }

    @Override
    public void onSessionEnded(SessionEndedRequest sessionEndedRequest, Session session) throws SpeechletException {

    }
}
