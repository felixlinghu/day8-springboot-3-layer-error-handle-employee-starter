package com.example.demo.repository;

import com.example.demo.entity.Employee;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEmployeeRepository extends JpaRepository<Employee, Integer> {

  List<Employee> findEmployeesByGender(String gender);

  List<Employee> findEmployeesByGender(String gender, Pageable pageable);
}
