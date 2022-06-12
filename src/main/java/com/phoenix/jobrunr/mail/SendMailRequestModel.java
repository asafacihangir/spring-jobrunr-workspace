package com.phoenix.jobrunr.mail;

import com.phoenix.jobrunr.service.model.FileResponseModel;
import java.util.List;

public class SendMailRequestModel {

  private List<String> cc;
  private List<String> bcc;
  private List<String> to;
  private String subject;
  private String text;
  private MailSenderInfoModelProperties mailInfo;
  private FileResponseModel fileResponse;

  private SendMailRequestModel(SendMailRequestBuilder builder) {
    this.cc = builder.cc;
    this.bcc = builder.bcc;
    this.to = builder.to;
    this.subject = builder.subject;
    this.text = builder.text;
    this.mailInfo = builder.mailInfo;
    this.fileResponse = builder.fileResponse;
  }

  public List<String> getCc() {
    return cc;
  }

  public List<String> getBcc() {
    return bcc;
  }

  public List<String> getTo() {
    return to;
  }

  public String getSubject() {
    return subject;
  }

  public String getText() {
    return text;
  }

  public MailSenderInfoModelProperties getMailInfo() {
    return mailInfo;
  }

  public FileResponseModel getFileResponse() {
    return fileResponse;
  }

  public static class SendMailRequestBuilder {
    private List<String> cc;
    private List<String> bcc;
    private List<String> to;
    private String subject;
    private String text;
    private MailSenderInfoModelProperties mailInfo;
    private FileResponseModel fileResponse;

    public SendMailRequestBuilder() {}

    public SendMailRequestBuilder cc(List<String> cc) {
      this.cc = cc;
      return this;
    }

    public SendMailRequestBuilder bcc(List<String> bcc) {
      this.bcc = bcc;
      return this;
    }

    public SendMailRequestBuilder to(List<String> to) {
      this.to = to;
      return this;
    }

    public SendMailRequestBuilder subject(String subject) {
      this.subject = subject;
      return this;
    }

    public SendMailRequestBuilder text(String text) {
      this.text = text;
      return this;
    }

    public SendMailRequestBuilder mailInfo(MailSenderInfoModelProperties mailInfo) {
      this.mailInfo = mailInfo;
      return this;
    }

    public SendMailRequestBuilder fileResponse(FileResponseModel fileResponse) {
      this.fileResponse = fileResponse;
      return this;
    }
    // Return the finally consrcuted User object
    public SendMailRequestModel build() {
      SendMailRequestModel sendMailRequestModel = new SendMailRequestModel(this);

      validateModel(sendMailRequestModel);
      return sendMailRequestModel;
    }

    private void validateModel(SendMailRequestModel sendMailRequestModel) {}
  }
}
