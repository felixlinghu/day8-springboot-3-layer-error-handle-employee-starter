package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String name;

  private Integer age;
  private String gender;

  private Double salary;
  private boolean active = true;
  @Column
  private Integer companyId;

  public Employee(String name, Integer age, String gender, Double salary) {
    this.name = name;
    this.age = age;
    this.gender = gender;
    this.salary = salary;
  }
}
