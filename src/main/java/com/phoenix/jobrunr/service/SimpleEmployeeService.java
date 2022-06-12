package com.phoenix.jobrunr.service;

import com.github.javafaker.Faker;
import com.phoenix.jobrunr.domain.Employee;
import com.phoenix.jobrunr.repository.EmployeeRepository;
import com.phoenix.jobrunr.service.base.EmployeeService;
import java.util.stream.Stream;
import org.jobrunr.jobs.annotations.Job;
import org.jobrunr.scheduling.BackgroundJob;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SimpleEmployeeService implements EmployeeService {

  private final EmployeeRepository repository;

  public SimpleEmployeeService(EmployeeRepository repository) {
    this.repository = repository;
  }


  @Override
  public void createEmployeeData() {
    final Faker faker = new Faker();
    for (int i = 0; i < 10; i++) {
      repository.save(new Employee(faker.name().firstName(), faker.name().lastName(),
          "asefacihangir@gmail.com"));
    }
  }

  @Transactional(readOnly = true)
  @Job(name = "Generate and send salary slip to all employees")
  @Override
  public void generateAndSendSalarySlipToAllEmployees() {
    final Stream<Long> allEmployees = repository.getAllEmployeeIds();
    BackgroundJob.enqueue(allEmployees,
        SalarySlipService::generateAndSendSalarySlip);
  }

}
