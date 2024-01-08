package com.personal.digital.commservice.service;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * This class represents a service for sending SMS messages using Twilio API.
 */
@Component
public class SMSService {

  private static final Logger logger = LoggerFactory.getLogger(SMSService.class);

  @Value("${twilio.phone.number}")
  private String fromNumber;

  /**
   * Sends an SMS message using the Twilio API.
   *
   * @param toNumber      the phone number to send the message to
   * @param messageText   the text of the message
   */
  public void sendSMS(String toNumber, String messageText) {
    Message message = createMessage(toNumber, fromNumber, messageText);

    // Print the Twilio-provided ID of the message
    logger.info(message.getSid());
  }

  /**
   * Creates a new Message object to send an SMS message using the Twilio API.
   *
   * @param to           the phone number to send the message to
   * @param from         the phone number to send the message from
   * @param messageText  the text of the message
   * @return the newly created Message object
   */
  private Message createMessage(String to, String from, String messageText) {
    return Message.creator(
            new PhoneNumber(to), // to
            new PhoneNumber(from), // from
            messageText)
        .create();
  }
}
