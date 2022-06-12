package com.phoenix.jobrunr.service;

import static java.lang.String.format;
import static java.time.LocalDate.now;

import com.phoenix.jobrunr.domain.Employee;
import com.phoenix.jobrunr.mail.EmailService;
import com.phoenix.jobrunr.mail.MailSenderInfoModelProperties;
import com.phoenix.jobrunr.mail.SendMailRequestModel;
import com.phoenix.jobrunr.repository.EmployeeRepository;
import com.phoenix.jobrunr.service.model.FileResponseModel;
import com.phoenix.jobrunr.service.model.SalarySlip;
import com.phoenix.jobrunr.service.model.WorkWeek;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import javax.mail.MessagingException;
import org.jobrunr.jobs.annotations.Job;
import org.springframework.stereotype.Component;

@Component
public class SalarySlipService {

  private static final Path salarySlipTemplatePath = Path.of(
      "src/main/resources/templates/salary-slip-template.docx");

  private final EmployeeRepository employeeRepository;
  private final TimeClockService timeClockService;
  private final DocumentGenerationService documentGenerationService;
  private final EmailService emailService;

  private final MailSenderInfoModelProperties mailSenderInfoModelProperties;

  public SalarySlipService(EmployeeRepository employeeRepository, TimeClockService timeClockService,
      DocumentGenerationService documentGenerationService, EmailService emailService,
      MailSenderInfoModelProperties mailSenderInfoModelProperties) {
    this.employeeRepository = employeeRepository;
    this.timeClockService = timeClockService;
    this.documentGenerationService = documentGenerationService;
    this.emailService = emailService;
    this.mailSenderInfoModelProperties = mailSenderInfoModelProperties;
  }



  @Job(name = "Generate and send salary slip to employee %0")
  public void generateAndSendSalarySlip(Long employeeId) throws Exception {
    final Employee employee = getEmployee(employeeId);
    final ByteArrayOutputStream byteArrayOutputStream = generateSalarySlip(employee);
    sendSalarySlip(employee, byteArrayOutputStream);
  }

  private void sendSalarySlip(Employee employee, ByteArrayOutputStream byteArrayOutputStream)
      throws MessagingException, IOException {

    final String mailContent = format(
        "Dear %s,\n\nhere you can find your weekly salary slip. \n \nThanks again for your hard work,\n Acme corp",
        employee.getFirstName());


    final String filename = employee.getFirstName() + "_" +employee.getLastName() +  ".docx";

    SendMailRequestModel sendMailRequestModel = new SendMailRequestModel.SendMailRequestBuilder()
        .cc(Collections.emptyList())
        .bcc(Collections.emptyList())
        .to(List.of(employee.getEmail()))
        .subject("Your weekly salary slip")
        .text(mailContent)
        .fileResponse(createFileResponse(filename, byteArrayOutputStream))
        .mailInfo(mailSenderInfoModelProperties)
        .build();

    emailService.sendHtmlMessage(sendMailRequestModel);

  }

  private FileResponseModel createFileResponse(String filename, ByteArrayOutputStream byteArrayOutputStream)
      throws IOException {

    FileResponseModel responseModel = new FileResponseModel();
    responseModel.setContentType("application/docx");
    responseModel.setFilename(filename);
    responseModel.setFileContent(byteArrayOutputStream.toByteArray());
    responseModel.setExtension("docx");

    return responseModel;
  }

  private ByteArrayOutputStream generateSalarySlip(Employee employee) throws Exception {
    final WorkWeek workWeek = getWorkWeekForEmployee(employee.getId());
    final SalarySlip salarySlip = new SalarySlip(employee, workWeek);
    return generateSalarySlipDocumentUsingTemplate(salarySlip);
  }

  private ByteArrayOutputStream generateSalarySlipDocumentUsingTemplate(SalarySlip salarySlip) throws Exception {
    Path salarySlipPath = Paths.get(System.getProperty("java.io.tmpdir"),
        String.valueOf(now().getYear()),
        format("workweek-%d", salarySlip.getWorkWeek().getWeekNbr()),
        format("salary-slip-employee-%d.pdf", salarySlip.getEmployee().getId()));
    return documentGenerationService.generateDocument(salarySlipTemplatePath, salarySlipPath, salarySlip);
  }

  private WorkWeek getWorkWeekForEmployee(Long employeeId) {
    return timeClockService.getWorkWeekForEmployee(employeeId);
  }

  private Employee getEmployee(Long employeeId) {
    return employeeRepository.findById(employeeId).orElseThrow(() -> new IllegalArgumentException(
        format("Employee with id '%d' does not exist", employeeId)));
  }

}
