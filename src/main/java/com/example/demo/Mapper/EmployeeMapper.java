package com.example.demo.Mapper;

import com.example.demo.dto.EmployeeRespose;
import com.example.demo.entity.Employee;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

  public EmployeeRespose toEmployeeResponse(Employee employee) {
    EmployeeRespose employeeRespose = new EmployeeRespose();
    BeanUtils.copyProperties(employee, employeeRespose);
    return employeeRespose;
  }
  public List<EmployeeRespose> toEmployeeResponses(List<Employee> employees){
    return employees.stream().map(this::toEmployeeResponse).toList();
  }
}
