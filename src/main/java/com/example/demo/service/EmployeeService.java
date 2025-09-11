package com.example.demo.service;

import com.example.demo.Mapper.EmployeeMapper;
import com.example.demo.dto.EmployeeResponse;
import com.example.demo.entity.Employee;
import com.example.demo.exception.InvalidDataMessageException;
import com.example.demo.repository.IEmployeeRepository;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EmployeeService {

  private final IEmployeeRepository employeeRepository;
  private final EmployeeMapper employeeMapper;

  public EmployeeService(IEmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
    this.employeeRepository = employeeRepository;
    this.employeeMapper = employeeMapper;
  }

  public List<EmployeeResponse> getEmployees(String gender, Integer page, Integer size) {
    List<Employee> employees;
    if (gender == null) {
      if (page == null || size == null) {
        employees = employeeRepository.findAll();
      } else {
        Pageable pageable = PageRequest.of(page - 1, size);
        employees = employeeRepository.findAll(pageable).toList();
      }
    } else {
      if (page == null || size == null) {
        employees = employeeRepository.findEmployeesByGender(gender);

      } else {
        Pageable pageable = PageRequest.of(page - 1, size);
        employees = employeeRepository.findEmployeesByGender(gender, pageable);
      }
    }
    return employeeMapper.toEmployeeResponses(employees);
  }

  public EmployeeResponse getEmployeeById(int id) throws ResponseStatusException {
    Employee employee = employeeRepository.findById(id).orElseThrow(() -> new InvalidDataMessageException("Employee not found with id: " + id));
    return employeeMapper.toEmployeeResponse(employee);
  }

  public EmployeeResponse createEmployee(Employee employee) throws Exception {
    if (employee.getAge() > 65 || employee.getAge() < 18) {
      throw new Exception("age is invalid");
    }
    if (employee.getAge() > 30 && (employee.getSalary() == null || employee.getSalary() < 20000)) {
      throw new InvalidDataMessageException("salary is invalid");
    }
    employee.setActive(true);
    return employeeMapper.toEmployeeResponse(employeeRepository.save(employee));
  }

  public EmployeeResponse updateEmployeeById(int id, Employee updatedEmployee) throws InvalidDataMessageException {
    Employee employee = getEmpolyee(id);
    if (employee.isActive()) {
      updatedEmployee.setId(id);
      return employeeMapper.toEmployeeResponse(employeeRepository.save(updatedEmployee));
    }
    throw new InvalidDataMessageException("Employee is not active with id: " + id);

  }

  public void deleteEmployeeById(int id) throws InvalidDataMessageException {
    Employee employee = getEmpolyee(id);
    employee.setActive(false);
    employee.setId(id);
    employeeRepository.save(employee);
  }

  private Employee getEmpolyee(int id) {
    return employeeRepository.findById(id).orElseThrow(() -> new InvalidDataMessageException("Employee not found with id: " + id));
  }
}
