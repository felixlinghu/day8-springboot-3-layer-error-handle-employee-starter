package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponse {
  private Integer id;
  private String name;
  private Integer age;
  private String gender;
  private Double salary;
  private boolean active=true;
  private Integer companyId;

}
