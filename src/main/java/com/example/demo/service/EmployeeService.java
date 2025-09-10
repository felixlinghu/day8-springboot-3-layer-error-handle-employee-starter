package com.example.demo.service;

import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EmployeeService {
  private final EmployeeRepository employeeRepository;

  public EmployeeService(EmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  public void clear() {
    employeeRepository.clear();
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
    Employee employee=employeeRepository.getEmployeeById(id);
   if(employee==null) {
     throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found with id: " + id);
   }
    return employee;
  }

  public Employee createEmployee(Employee employee) {

    return  employeeRepository.create(employee);
  }

  public Employee updateEmployeeById(int id, Employee updatedEmployee) {
    if(employeeRepository.getEmployeeById(id)==null){
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found with id: " + id);
    }
   return employeeRepository.updateEmployeeById(id,updatedEmployee);

  }

  public void deleteEmployeeById(int id) {
    if(employeeRepository.getEmployeeById(id)==null){
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found with id: " + id);
    }
    employeeRepository.deleteEmployeeById(id);
  }
}
