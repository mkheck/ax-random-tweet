package org.thehecklers;

import com.amazon.speech.speechlet.servlet.SpeechletServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AxRandomTweetApplication {
    @Autowired
	private TweetSpeechlet speechlet;

    @Bean
    public ServletRegistrationBean registerServlet() {
        SpeechletServlet speechletServlet = new SpeechletServlet();
        speechletServlet.setSpeechlet(this.speechlet);

        return new ServletRegistrationBean(speechletServlet, "/alexa");
    }

    public static void main(String[] args) {
        SpringApplication.run(AxRandomTweetApplication.class, args);
    }
}
