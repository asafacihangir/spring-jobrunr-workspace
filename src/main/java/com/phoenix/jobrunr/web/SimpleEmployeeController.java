package com.phoenix.jobrunr.web;

import com.phoenix.jobrunr.service.base.EmployeeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employee")
public class SimpleEmployeeController {


  private final EmployeeService employeeService;

  public SimpleEmployeeController(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }


  @GetMapping("/create")
  public void createEmployeeData() {
      employeeService.createEmployeeData();
  }

  @GetMapping("/run-salary-job")
  public void generateAndSendSalarySlipToAllEmployees() {
      employeeService.generateAndSendSalarySlipToAllEmployees();
  }


}
