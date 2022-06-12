package com.phoenix.jobrunr.mail;

import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailConfiguration {

  private final MailSenderInfoModelProperties mailSenderInfo;

  public EmailConfiguration(MailSenderInfoModelProperties mailSenderInfo) {
    this.mailSenderInfo = mailSenderInfo;
  }

  @Bean
  JavaMailSender javaMailSender() {
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost(mailSenderInfo.getHost());
    mailSender.setPort(Integer.parseInt(mailSenderInfo.getPort()));
    mailSender.setUsername(mailSenderInfo.getUsername());
    mailSender.setPassword(mailSenderInfo.getPassword());
    Properties javaMailProperties = new Properties();
    javaMailProperties.put("mail.smtp.auth", "true");
    javaMailProperties.put("mail.smtp.starttls.enable", "true");
    //javaMailProperties.put("mail.smtp.ssl.enable", "true");

    mailSender.setJavaMailProperties(javaMailProperties);
    return mailSender;
  }

}
