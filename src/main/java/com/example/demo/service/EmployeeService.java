package com.example.demo.service;

import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;
import java.util.List;
import java.util.stream.Stream;
import jdk.jfr.DataAmount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EmployeeService {

  private final EmployeeRepository employeeRepository;

  public void clear() {
    employeeRepository.clear();
  }

  public EmployeeService(EmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  public List<Employee> getEmployees(String gender, Integer page, Integer size) {
    Stream<Employee> stream = employeeRepository.getEmployees().stream();
    if (gender != null) {
      stream = stream.filter(employee -> employee.getGender().compareToIgnoreCase(gender) == 0);
    }
    if (page != null && size != null) {
      stream = stream.skip((long) (page - 1) * size).limit(size);
    }
    return stream.toList();
  }

  public Employee getEmployeeById(int id) throws ResponseStatusException {
    Employee employee = employeeRepository.getEmployeeById(id);
    if (employee == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found with id: " + id);
    }
    return employee;
  }

  public Employee createEmployee(Employee employee) throws Exception {
    if (employee.getAge() > 65 || employee.getAge() < 18) {
      throw new Exception("age is invalid");
    }
    if (employee.getAge() > 30 && (employee.getSalary() == null || employee.getSalary() < 20000)) {
//      throw new RuntimeException("salary is invalid");
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "salary is invalid");
    }
    employee.setActive(true);
    return employeeRepository.create(employee);
  }

  public Employee updateEmployeeById(int id, Employee updatedEmployee) {
    Employee employee = employeeRepository.getEmployeeById(id);
    if (employee == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found with id: " + id);
    }
    if (employee.isActive()) {
      return employeeRepository.updateEmployeeById(id, updatedEmployee);
    }
    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found with id: " + id);

  }

  public void deleteEmployeeById(int id) {
    Employee employee = employeeRepository.getEmployeeById(id);
    if (employee == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found with id: " + id);
    }
    employee.setActive(false);
    employeeRepository.updateEmployeeById(employee.getId(),employee);
  }
}
