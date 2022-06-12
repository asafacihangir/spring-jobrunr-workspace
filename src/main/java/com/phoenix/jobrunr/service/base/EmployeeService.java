package com.phoenix.jobrunr.service.base;

import org.jobrunr.jobs.annotations.Job;
import org.springframework.transaction.annotation.Transactional;

public interface EmployeeService {

  void createEmployeeData();

  @Transactional(readOnly = true)
  @Job(name = "Generate and send salary slip to all employees")
  void generateAndSendSalarySlipToAllEmployees();
}
