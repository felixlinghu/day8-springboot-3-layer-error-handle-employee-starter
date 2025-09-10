package com.example.demo.service;

import com.example.demo.entity.Employee;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EmployeeService {
  private final List<Employee> employees = new ArrayList<>();

  public void clear() {
    employees.clear();
  }

  public List<Employee> getEmployees(String gender, Integer page, Integer size) {
    Stream<Employee> stream = employees.stream();
    if (gender != null) {
      stream = stream.filter(employee -> employee.getGender().compareToIgnoreCase(gender) == 0);
    }
    if (page != null && size != null) {
      stream = stream.skip((long) (page - 1) * size).limit(size);
    }
    return stream.toList();
  }

  public Employee getEmployeeById(int id) {
    return employees.stream()
        .filter(employee -> employee.getId() == id)
        .findFirst()
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found with id: " + id));
  }

  public Employee createEmployee(Employee employee) {
    employee.setId(employees.size() + 1);
    employees.add(employee);
    return employee;
  }

  public Employee updateEmployee(int id, Employee updatedEmployee) {
    Employee found = null;
    for (Employee e : employees) {
      if (Objects.equals(e.getId(), id)) {
        found = e;
        break;
      }
    }
    if (found == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found with id: " + id);
    }
    found.setName(updatedEmployee.getName());
    found.setAge(updatedEmployee.getAge());
    found.setGender(updatedEmployee.getGender());
    found.setSalary(updatedEmployee.getSalary());
    return found;
  }

  public void deleteEmployee(int id) {
    Employee found = null;
    for (Employee e : employees) {
      if (e.getId() == id) {
        found = e;
        break;
      }
    }
    if (found == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found with id: " + id);
    }
    employees.remove(found);
  }
}
