package com.personal.digital.commservice;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The CommserviceApplication class is the entry point for the communication service application.
 * It is responsible for configuring the Twilio account credentials and running the Spring Boot application.
 */
@SpringBootApplication
public class CommserviceApplication {

  public static void main(String[] args) {
    Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
    System.setProperty("twilio.account.sid", dotenv.get("TWILIO_ACCOUNT_SID"));
    System.setProperty("twilio.auth.token", dotenv.get("TWILIO_AUTH_TOKEN"));
    System.setProperty("twilio.phone.number", dotenv.get("TWILIO_PHONE_NUMBER"));
    System.setProperty("apikey", dotenv.get("API_KEY"));
    SpringApplication.run(CommserviceApplication.class, args);
  }
}
