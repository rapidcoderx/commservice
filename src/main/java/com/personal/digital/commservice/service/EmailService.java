package com.personal.digital.commservice.service;

import com.personal.digital.commservice.dto.EmailRequestDTO;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
  private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

  @Value("${sendgridkey}")
  private String sendgridkey;

  public void sendEmail(EmailRequestDTO request) {
    Mail mail = constructEmail(request);
    SendGrid sg = new SendGrid(sendgridkey);
    Request requestSg = new Request();

    try {
      requestSg.setMethod(Method.POST);
      requestSg.setEndpoint("mail/send");
      requestSg.setBody(mail.build());
      Response response = sg.api(requestSg);
      logger.info(
          "Email sent! Response status : {}, body : {}",
          response.getStatusCode(),
          response.getBody());
    } catch (IOException ex) {
      logger.error("Failed to send email", ex);
    }
  }

  // Extracted method to create mail
  private Mail constructEmail(EmailRequestDTO request) {
    Email from = new Email("sathishkrishnan@digisco.dev");
    Email to = new Email(request.getEmail());
    Content content = new Content("text/html", request.getData());
    Mail mail = new Mail(from, request.getData(), to, content);
    mail.setTemplateId("d-3dbaac72c85f4a369f30235dceb40bc7");
    mail.getFrom().setName("Demo Bank");

    final Personalization personalization = new Personalization();
    personalization.addTo(new Email(request.getEmail()));
    personalization.getTos().get(0).setName(request.getName());
    personalization.setSubject(request.getData());
    logger.info("Email sent! Response status : {}, body : {}", request.getName(), request.getAccountno());

    personalization.addDynamicTemplateData("cname", request.getName());
    personalization.addDynamicTemplateData("acctnum", request.getAccountno());
    mail.addPersonalization(personalization);
    mail.personalization.get(0).addDynamicTemplateData("cname", request.getName());
    mail.personalization.get(0).addDynamicTemplateData("acctnum", request.getAccountno());

    mail.setReplyTo(new Email(request.getEmail()));
    return mail;
  }
}
