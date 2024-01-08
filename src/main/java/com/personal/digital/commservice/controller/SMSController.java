package com.personal.digital.commservice.controller;

import com.personal.digital.commservice.service.SMSRequestDTO;
import com.personal.digital.commservice.service.SMSService;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sms")
public class SMSController {

  public static final String GENERIC_ERROR_MESSAGE =
      "An error occurred while processing your request.";
  public static final String RESPONSE_MESSAGE_KEY = "message";
  public static final String API_KEY_HEADER = "X-api-key";
  private static final Logger logger = LoggerFactory.getLogger(SMSController.class);
  private final SMSService smsService;

  @Value("${apikey}")
  private String validApiKey;

  public SMSController(SMSService smsService) {
    this.smsService = smsService;
  }

  @PostMapping(path = "/send", produces = "application/json", consumes = "application/json")
  public ResponseEntity<Map<String, String>> sendSMS(
      @RequestHeader(API_KEY_HEADER) String apiKey, @RequestBody SMSRequestDTO smsRequest) {

    if (!apiKey.equals(validApiKey)) {
      return new ResponseEntity<>(
          Map.of(RESPONSE_MESSAGE_KEY, "Invalid or missing API key."), HttpStatus.UNAUTHORIZED);
    }

    if (smsRequest == null
        || smsRequest.getToNumber() == null
        || smsRequest.getAccountNumber() == null) {
      return new ResponseEntity<>(
          Map.of(RESPONSE_MESSAGE_KEY, "Invalid request."), HttpStatus.BAD_REQUEST);
    }

    Map<String, String> response = new HashMap<>();
    try {
      logger.info("Sending SMS to recipient: {}", smsRequest.getToNumber());
      sendSMSToRecipient(smsRequest.getToNumber(), constructMessage(smsRequest.getAccountNumber()));
      response.put(RESPONSE_MESSAGE_KEY, "SMS sent successfully!");
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      response.put(RESPONSE_MESSAGE_KEY, generateErrorMessage());
      return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  private void sendSMSToRecipient(String to, String message) {
    smsService.sendSMS(to, message);
  }

  private String generateErrorMessage() {
    // Return generic error message
    return GENERIC_ERROR_MESSAGE;
  }

  private String constructMessage(String accountNumber) {
    return String.format("Savings account %s opened successfully!", accountNumber);
  }
}
