package com.example.demo.service;

import com.example.demo.entity.Employee;
import com.example.demo.exception.InvalidDataMessageException;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.IEmployeeRepository;
import java.util.List;
import java.util.stream.Stream;
import jdk.jfr.DataAmount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EmployeeService {

  private final IEmployeeRepository employeeRepository;

  public void clear() {
    employeeRepository.flush();
  }

  public EmployeeService(IEmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  public List<Employee> getEmployees(String gender, Integer page, Integer size) {
    if (gender == null) {
      if (page == null || size == null) {
        return employeeRepository.findAll();
      } else {
        Pageable pageable = PageRequest.of(page - 1, size);
        return employeeRepository.findAll(pageable).toList();
      }
    } else {
      if (page == null || size == null) {
        return employeeRepository.findEmployeesByGender(gender);

      } else {
        Pageable pageable = PageRequest.of(page - 1, size);
        return employeeRepository.findEmployeesByGender(gender, pageable);
      }
    }
  }

  public Employee getEmployeeById(int id) throws ResponseStatusException {
    return employeeRepository.findById(id).orElseThrow(() -> new InvalidDataMessageException("Employee not found with id: " + id));
  }

  public Employee createEmployee(Employee employee) throws Exception {
    if (employee.getAge() > 65 || employee.getAge() < 18) {
      throw new Exception("age is invalid");
    }
    if (employee.getAge() > 30 && (employee.getSalary() == null || employee.getSalary() < 20000)) {
      throw new InvalidDataMessageException("salary is invalid");
    }
    employee.setActive(true);
    return employeeRepository.save(employee);
  }

  public Employee updateEmployeeById(int id, Employee updatedEmployee) throws InvalidDataMessageException {
    Employee employee = employeeRepository.findById(id).orElseThrow(() -> new InvalidDataMessageException("Employee not found with id: " + id));
    if (employee.isActive()) {
      updatedEmployee.setId(id);
      return employeeRepository.save(updatedEmployee);
    }
    throw new InvalidDataMessageException("Employee is not active with id: " + id);

  }

  public void deleteEmployeeById(int id) throws InvalidDataMessageException {
    Employee employee = employeeRepository.findById(id).orElseThrow(() -> new InvalidDataMessageException("Employee not found with id: " + id));
    employee.setActive(false);
    employee.setId(id);
    employeeRepository.save(employee);
  }
}
