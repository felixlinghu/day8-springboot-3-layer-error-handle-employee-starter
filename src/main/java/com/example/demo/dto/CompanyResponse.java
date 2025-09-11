package com.example.demo.dto;

import com.example.demo.entity.Employee;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyResponse {
  private Integer id;
  private String name;
  private List<Employee> employees;

}
