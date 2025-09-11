package com.example.demo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequest {

  private Integer id;
  private String name;
  private Integer age;
  private String gender;
  @NotNull(message = "Gender cannot be null")
  @Min(value = 0, message = "Salary must be positive number")
  private Double salary;
  private boolean active = true;
  private Integer companyId;

  public EmployeeRequest(String name, Integer age, String gender, Double salary) {
    this.name = name;
    this.age = age;
    this.gender = gender;
    this.salary = salary;
  }
}
