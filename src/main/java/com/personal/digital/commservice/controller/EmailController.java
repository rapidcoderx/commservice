package com.personal.digital.commservice.controller;

import com.personal.digital.commservice.dto.EmailRequestDTO;
import com.personal.digital.commservice.service.EmailService;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
public class EmailController {

  public static final String GENERIC_ERROR_MESSAGE =
      "An error occurred while processing your request.";
  public static final String RESPONSE_MESSAGE_KEY = "message";
  public static final String API_KEY_HEADER = "X-api-key";

  private static final Logger logger = LoggerFactory.getLogger(EmailController.class);
  private final EmailService emailService;

  @Value("${apikey}")
  private String validApiKey;

  @Autowired
  public EmailController(EmailService emailService) {
    this.emailService = emailService;
  }

  @PostMapping("/send")
  public ResponseEntity<Map<String, String>> sendEmail(
      @RequestHeader(API_KEY_HEADER) String apiKey, @RequestBody EmailRequestDTO emailRequest) {

    if (!apiKey.equals(validApiKey)) {
      return new ResponseEntity<>(
          Map.of(RESPONSE_MESSAGE_KEY, "Invalid or missing API key."), HttpStatus.UNAUTHORIZED);
    }

    if (emailRequest == null
        || emailRequest.getAccountno() == null
        || emailRequest.getName() == null
        || emailRequest.getEmail() == null
        || emailRequest.getData() == null) {
      return new ResponseEntity<>(
          Map.of(RESPONSE_MESSAGE_KEY, "Invalid request."), HttpStatus.BAD_REQUEST);
    }

    Map<String, String> response = new HashMap<>();
    try {
      logger.info("Sending email to recipient: {}", emailRequest.getEmail());
      emailService.sendEmail(emailRequest);
      response.put(RESPONSE_MESSAGE_KEY, "Email sent successfully!");
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      response.put(RESPONSE_MESSAGE_KEY, generateErrorMessage());
      return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  private String generateErrorMessage() {
    // Return generic error message
    return GENERIC_ERROR_MESSAGE;
  }
}
