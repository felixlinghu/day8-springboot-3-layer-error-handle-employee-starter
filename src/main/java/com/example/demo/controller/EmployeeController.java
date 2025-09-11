package com.example.demo.controller;

import com.example.demo.dto.EmployeeRespose;
import com.example.demo.entity.Employee;
import com.example.demo.exception.InvalidDataMessageException;
import com.example.demo.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @GetMapping
    public List<EmployeeRespose> getEmployees(@RequestParam(required = false) String gender, @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
        return employeeService.getEmployees(gender,page,size);
    }

    @GetMapping("/{id}")
    public EmployeeRespose getEmployeeById(@PathVariable int id) {
        return employeeService.getEmployeeById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeRespose createEmployee(@RequestBody Employee employee) throws Exception {
       return employeeService.createEmployee(employee);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeRespose updateEmployee(@PathVariable int id, @RequestBody Employee updatedEmployee) throws InvalidDataMessageException {
       return employeeService.updateEmployeeById(id, updatedEmployee);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable int id) throws InvalidDataMessageException {
      employeeService.deleteEmployeeById(id);
    }

}
