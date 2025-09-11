package com.example.demo.repository;

import com.example.demo.entity.Employee;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeRepository {
  private final List<Employee> employees = new ArrayList<>();

  public  Employee getEmployeeById(int id) {
    return employees.stream()
        .filter(employee -> employee.getId() == id)
        .findFirst().orElse(null);
  }


  public List<Employee> getEmployees() {
    return employees;
  }

  public Employee create(Employee employee) {
    employee.setId(employees.size() + 1);
    employees.add(employee);
    return employee;
  }

  public Employee updateEmployeeById(int id, Employee updatedEmployee) {
    Employee found = getEmployeeById(id);
    found.setName(updatedEmployee.getName());
    found.setAge(updatedEmployee.getAge());
    found.setGender(updatedEmployee.getGender());
    found.setSalary(updatedEmployee.getSalary());
    return found;
  }

  public void deleteEmployeeById(int id) {
    employees.removeIf(employee -> employee.getId()==id);
  }
}
