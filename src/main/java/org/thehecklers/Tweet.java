package org.thehecklers;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by markheckler on 3/5/17.
 */
@Data
@AllArgsConstructor
public class Tweet {
    private String text, inReplyToScreenName, quotedTweet;
}
