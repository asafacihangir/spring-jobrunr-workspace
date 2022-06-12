package com.phoenix.jobrunr.mail;

import java.util.Objects;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class SimpleEmailService implements EmailService {

  private static final Logger logger = LoggerFactory.getLogger(SimpleEmailService.class);

  private final JavaMailSender javaMailSender;

  public SimpleEmailService(JavaMailSender javaMailSender) {
    this.javaMailSender = javaMailSender;
  }


  @Override
  public boolean sendHtmlMessage(SendMailRequestModel requestModel) {
    boolean success = true;
    logger.info("sending email...");
    try {
      MimeMessage message = javaMailSender.createMimeMessage();
      message.setHeader("Content-Type", "text/html;charset=iso-8859-9");
      message.setSubject(requestModel.getSubject(), "utf-8");
      message.setContent(message, "text/html;charset=iso-8859-9");
      MimeMessageHelper helper;

      if (Objects.isNull(requestModel.getFileResponse())) {
        helper = new MimeMessageHelper(message, false, "iso-8859-9");
      } else {
        helper = new MimeMessageHelper(message, true, "iso-8859-9");
        helper.addAttachment(
            requestModel.getFileResponse().getFilename(),
            new ByteArrayResource(requestModel.getFileResponse().getFileContent()));
      }

      helper.setFrom(requestModel.getMailInfo().getUsername());
      helper.setText(requestModel.getText(), true);
      String[] toList = requestModel.getTo().toArray(new String[0]);
      helper.setTo(toList);
      String[] ccList = requestModel.getCc().toArray(new String[0]);
      helper.setCc(ccList);
      String[] bccList = requestModel.getBcc().toArray(new String[0]);
      helper.setBcc(bccList);
      message.saveChanges();
      javaMailSender.send(message);

      logger.info("sent email successfully.");

    } catch (MailException | MessagingException exception) {
      logger.warn("sending email failed");
      exception.printStackTrace();
      success = false;
    }
    return success;
  }


}
