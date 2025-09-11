package com.example.demo.Mapper;

import com.example.demo.dto.EmployeeResponse;
import com.example.demo.entity.Employee;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

  public EmployeeResponse toEmployeeResponse(Employee employee) {
    EmployeeResponse employeeRespose = new EmployeeResponse();
    BeanUtils.copyProperties(employee, employeeRespose);
    return employeeRespose;
  }

  public List<EmployeeResponse> toEmployeeResponses(List<Employee> employees) {
    return employees.stream().map(this::toEmployeeResponse).toList();
  }
}
