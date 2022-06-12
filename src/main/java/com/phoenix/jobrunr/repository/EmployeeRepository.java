package com.phoenix.jobrunr.repository;

import com.phoenix.jobrunr.domain.Employee;
import java.util.stream.Stream;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {

  @Query("select e.id from Employee e")
  Stream<Long> getAllEmployeeIds();

}