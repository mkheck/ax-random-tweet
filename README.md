# README #

##ax-random-tweet##

###Alexa skill to provide a random tweet from a specified public Twitter account###

Spring Boot-based application, deployable to Cloud Foundry.

Due to decisions Amazon made with their voice recognition/dictation engine, you may need to update the application to "gently nudge" Alexa into recognizing particular words/phrases:

* For one word, "English dictionary" Twitter handles, Alexa correctly passes along the Twitter account/handle if one is specified
* For compound word Twitter handles, e.g. [@TheCoffeeBean][https://twitter.com/TheCoffeeBean), Alexa splits the compound word (handle) into its simple component words, forcing us to reassemble to obtain tweets _from_ that account
* For handles such as mine [@mkheck](https://twitter.com/mkheck), which are usually stated as "M K Heck", we must perform some rather crude verification work in our application. Note that this is version 1, so it's exceptionally crude at this point, but I'll be cleaning this up later. Really. :)

NOTE: Creating a list that helps Alexa match potential values for a particular "slot" (variable) doesn't limit Alexa's engine to those values. _In theory_ at least, it simply helps guide Alexa to choose those values when an input gets close. In practice, however, I don't see any real affinity to those words, requiring us to do a bit more of the lifting ourselves.

### How do I start? ###

This is a Maven-based project, so `git clone` it, load it in your favorite IDE (or just `cd` into its project directory on your machine), & `mvn package`. To deploy to Cloud Foundry, just `cf push`. You will need to define the following env vars to make the application run:

* APPLICATION_ID (your Alexa application Id, so you don't freely share your backend app with anyone who accesses it)
* TWITTER_KEY (Consumer Key (API Key))
* TWITTER_SECRET (Consumer Secret (API Secret))
* TWITTER_TOKEN (Access Token)
* TWITTER_TOKEN_SECRET (Access Token Secret)
* TWITTER_FROMUSER (optional, defaults to Pivotal for requests with no specified Twitter handle)

NOTE: Providing application-level access to Twitter is easily done and avoids awkward prompting by Alexa for Twitter credentials. Visit https://apps.twitter.com/ to configure your app and obtain the values shown above, then create environment variables with the values Twitter provides you. *Please do not embed these values in your application*, unless you would like to share them with the world the moment you push your updates back to Github. :O

### Developer ###

* Mark Heckler
* [@MkHeck](https://twitter.com/MkHeck)
* mark.heckler@gmail.com
