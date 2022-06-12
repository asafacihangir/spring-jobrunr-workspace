package com.phoenix.jobrunr.mail;

public interface EmailService {

  boolean sendHtmlMessage(SendMailRequestModel requestModel);
}
