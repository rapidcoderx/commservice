package com.personal.digital.commservice.config;

import com.twilio.Twilio;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/** This class initializes the Twilio client by setting the account SID and auth token. */
@Configuration
public class TwilioInitializer {

  @Value("${twilio.account.sid}")
  private String accountSid;

  @Value("${twilio.auth.token}")
  private String authToken;

  @PostConstruct
  public void init() {
   Twilio.init(accountSid, authToken);
  }
}
