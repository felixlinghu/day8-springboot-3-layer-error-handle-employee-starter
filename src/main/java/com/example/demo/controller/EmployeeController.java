package com.example.demo.controller;

import com.example.demo.dto.EmployeeResponse;
import com.example.demo.entity.Employee;
import com.example.demo.exception.InvalidDataMessageException;
import com.example.demo.service.EmployeeService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

  private final EmployeeService employeeService;

  public EmployeeController(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }


  @GetMapping
  public List<EmployeeResponse> getEmployees(@RequestParam(required = false) String gender, @RequestParam(required = false) Integer page,
      @RequestParam(required = false) Integer size) {
    return employeeService.getEmployees(gender, page, size);
  }

  @GetMapping("/{id}")
  public EmployeeResponse getEmployeeById(@PathVariable int id) {
    return employeeService.getEmployeeById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public EmployeeResponse createEmployee(@RequestBody Employee employee) throws Exception {
    return employeeService.createEmployee(employee);
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public EmployeeResponse updateEmployee(@PathVariable int id, @RequestBody Employee updatedEmployee) throws InvalidDataMessageException {
    return employeeService.updateEmployeeById(id, updatedEmployee);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteEmployee(@PathVariable int id) throws InvalidDataMessageException {
    employeeService.deleteEmployeeById(id);
  }

}
