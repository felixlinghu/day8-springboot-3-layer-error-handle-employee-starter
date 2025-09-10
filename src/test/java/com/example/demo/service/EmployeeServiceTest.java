package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

  @Mock
  private EmployeeRepository employeeRepository;
  @InjectMocks
  private EmployeeService employeeService;

  @Test
  void should_throw_exception_when_age_is_larger_65_or_smaller_18() throws Exception {
    //given
    Employee employee = new Employee("Tom", 80, null, null);
    //when
    assertThrows(Exception.class, () -> {
      employeeService.createEmployee(employee);
    });
    //then
    verifyNoInteractions(employeeRepository);
  }

  @Test
  void should_return_correct_message_when_age_valid() throws Exception {
    //given
    Employee employee = new Employee("Tom", 20, null, 2000.0);
    //when
    employeeService.createEmployee(employee);
    //then
    verify(employeeRepository).create(eq(employee));

  }

  @Test
  void should_throw_exception_when_age_is_larger_30_and_salary_is_smaller_20000() throws Exception {
    //given
    Employee employee = new Employee("Tom", 40, null, 300.0);
    //when
    assertThrows(Exception.class, () -> {
      employeeService.createEmployee(employee);
    });
    //then
    verifyNoInteractions(employeeRepository);
  }

  @Test
  void should_return_active_true_when_create_employee() throws Exception {
    //given
    Employee employee = spy(new Employee("Tom", 20, null, 2000.0));
    //when
    employeeService.createEmployee(employee);
    //then
    verify(employee).setActive(true);
    verify(employeeRepository).create(eq(employee));
  }

  @Test
  void should_return_active_false_when_delete() throws Exception {
    //given
    Employee employee = spy(new Employee("Tom", 20, null, 2000.0));
    EmployeeRepository repository = spy(new EmployeeRepository());
    EmployeeService employeeService = new EmployeeService(repository);
    employee = employeeService.createEmployee(employee);
    //when
    employeeService.deleteEmployeeById(employee.getId());
    //then
    verify(employee).setActive(false);
  }


  @Test
  void should_update_fail_when_update_not_active() throws Exception {
    //given
    Employee employee = spy(new Employee("Tom", 20, null, 2000.0));
    EmployeeRepository repository = spy(new EmployeeRepository());
    EmployeeService employeeService = new EmployeeService(repository);
    employee = employeeService.createEmployee(employee);
    employee.setActive(false);
    //when
    Employee updateEmployee = employeeService.updateEmployeeById(employee.getId(), employee);
    //then
    verify(repository, never()).updateEmployeeById(employee.getId(), employee);
    assertNull(updateEmployee);
  }

}