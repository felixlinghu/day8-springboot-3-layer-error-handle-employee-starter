package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.example.demo.Mapper.EmployeeMapper;
import com.example.demo.dto.EmployeeResponse;
import com.example.demo.entity.Employee;
import com.example.demo.repository.IEmployeeRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

  @Mock
  private EmployeeMapper employeeMapper;
  @Mock
  private IEmployeeRepository employeeRepository;
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
    verify(employeeRepository).save(eq(employee));

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
    verify(employeeRepository).save(eq(employee));
  }

  @Test
  void should_return_active_false_when_delete() throws Exception {
    //given
    Employee employee = new Employee("Tom", 20, null, 2000.0);
    employee.setId(1);
    when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
    //when
    employeeService.deleteEmployeeById(employee.getId());
    //then
    verify(employeeRepository).save(employee);
  }


  @Test
  void should_update_fail_when_update_not_active() throws Exception {
    //given
    Employee employee = new Employee("Tom", 20, null, 2000.0);
    employee.setActive(false);
    employee.setId(1);
    Optional<Employee> employee1 = Optional.of(employee);
    when(employeeRepository.findById(1)).thenReturn(employee1);
    //when
    try {
      EmployeeResponse updateEmployee = employeeService.updateEmployeeById(employee.getId(), employee);
      fail();
    }
    //then
    catch (Exception e) {
      verify(employeeRepository, never()).save(employee);
    }

  }

}