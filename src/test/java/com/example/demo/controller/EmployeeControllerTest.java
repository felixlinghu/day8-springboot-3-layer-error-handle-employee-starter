package com.example.demo.controller;

import com.example.demo.entity.Employee;
import com.google.gson.Gson;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @BeforeEach
  void setUp() throws Exception {
    mockMvc.perform(delete("/employees/clear"));
  }

  @Test
  void should_return_404_when_employee_not_found() throws Exception {
    mockMvc.perform(get("/employees/999")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  void should_return_all_employee() throws Exception {
    createEmployee("John Smith");
    createEmployee("Jane Doe");
    mockMvc.perform(get("/employees")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(2));
  }

  private Gson createEmployee(String name) throws Exception {
    Gson gson = new Gson();
    if (Objects.equals(name, "Jane Doe")) {
      String employee1 = gson.toJson(new Employee("Jane Doe", 22, "FEMALE", 60000.0)).toString();
      mockMvc.perform(post("/employees")
          .contentType(MediaType.APPLICATION_JSON)
          .content(employee1));
      return gson;
    }
    String employee1 = gson.toJson(new Employee("John Smith", 28, "MALE", 60000.0)).toString();
    mockMvc.perform(post("/employees")
        .contentType(MediaType.APPLICATION_JSON)
        .content(employee1));
    return gson;
  }

  @Test
  void should_return_employee_when_employee_found() throws Exception {
    Gson gson = new Gson();
    Employee expect = new Employee("John Smith", 28, "MALE", 60000.0);
    String employee1 = gson.toJson(expect).toString();
    ResultActions actions = mockMvc.perform(post("/employees")
        .contentType(MediaType.APPLICATION_JSON)
        .content(employee1));
    mockMvc.perform(get("/employees/1")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value(expect.getName()))
        .andExpect(jsonPath("$.age").value(expect.getAge()))
        .andExpect(jsonPath("$.gender").value(expect.getGender()))
        .andExpect(jsonPath("$.salary").value(expect.getSalary()));
  }

  @Test
  void should_return_male_employee_when_employee_found() throws Exception {
    Gson gson = new Gson();
    Employee expect = new Employee("John Smith", 28, "MALE", 60000.0);
    String employee1 = gson.toJson(expect).toString();
    ResultActions actions = mockMvc.perform(post("/employees")
        .contentType(MediaType.APPLICATION_JSON)
        .content(employee1));

    mockMvc.perform(get("/employees?gender=male")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].name").value(expect.getName()))
        .andExpect(jsonPath("$[0].age").value(expect.getAge()))
        .andExpect(jsonPath("$[0].gender").value(expect.getGender()))
        .andExpect(jsonPath("$[0].salary").value(expect.getSalary()));
  }

  @Test
  void should_create_employee() throws Exception {
    String requestBody = """
                {
                    "name": "John Smith",
                    "age": 28,
                    "gender": "MALE",
                    "salary": 60000
                }
        """;

    mockMvc.perform(post("/employees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").isNumber())
        .andExpect(jsonPath("$.name").value("John Smith"))
        .andExpect(jsonPath("$.age").value(28))
        .andExpect(jsonPath("$.gender").value("MALE"))
        .andExpect(jsonPath("$.salary").value(60000));
  }

  @Test
  void should_return_200_with_empty_body_when_no_employee() throws Exception {
    mockMvc.perform(get("/employees")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isEmpty());
  }

  @Test
  void should_return_200_with_employee_list() throws Exception {
    Gson gson = new Gson();
    Employee expect = new Employee("John Smith", 28, "MALE", 60000.0);
    String employee1 = gson.toJson(expect).toString();
    ResultActions actions = mockMvc.perform(post("/employees")
        .contentType(MediaType.APPLICATION_JSON)
        .content(employee1));
    mockMvc.perform(get("/employees")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(1))
        .andExpect(jsonPath("$[0].name").value(expect.getName()))
        .andExpect(jsonPath("$[0].age").value(expect.getAge()))
        .andExpect(jsonPath("$[0].gender").value(expect.getGender()))
        .andExpect(jsonPath("$[0].salary").value(expect.getSalary()));
  }

  @Test
  void should_status_204_when_delete_employee() throws Exception {
    createEmployee("John Smith");
    mockMvc.perform(delete("/employees/1"))
        .andExpect(status().isNoContent());
  }

  @Test
  void should_status_200_when_update_employee() throws Exception {
    createEmployee("John Smith");
    String requestBody = """
                {
                    "name": "John Smith",
                    "age": 29,
                    "gender": "MALE",
                    "salary": 65000.0
                }
        """;

    mockMvc.perform(put("/employees/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.age").value(29))
        .andExpect(jsonPath("$.salary").value(65000.0));
  }

  @Test
  void should_status_200_and_return_paged_employee_list() throws Exception {
    createEmployee("John Smith");
    createEmployee("Jane Doe");
    createEmployee("Jane Doe");
    createEmployee("Jane Doe");
    createEmployee("Jane Doe");
    createEmployee("Jane Doe");

    mockMvc.perform(get("/employees?page=1&size=5")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(5));
  }

  @Test
  void should_not_create_employee_when_employee_invalid() throws Exception {
    String requestBody = """
                {
                    "name": "John Smith",
                    "age": 38,
                    "gender": "MALE",
                    "salary": 600
                }
        """;

    mockMvc.perform(post("/employees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status().is4xxClientError());
  }

  @Test
  void should_create_employee_with_active_is_true() throws Exception {
    String requestBody = """
                {
                    "name": "John Smith",
                    "age": 28,
                    "gender": "MALE",
                    "salary": 60000
                }
        """;

    mockMvc.perform(post("/employees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").isNumber())
        .andExpect(jsonPath("$.name").value("John Smith"))
        .andExpect(jsonPath("$.age").value(28))
        .andExpect(jsonPath("$.gender").value("MALE"))
        .andExpect(jsonPath("$.salary").value(60000))
        .andExpect(jsonPath("$.active").value(true));

  }

  @Test
  void should_return_active_false_when_delete_employee() throws Exception {
    createEmployee("John Smith");
    mockMvc.perform(delete("/employees/1"))
        .andExpect(status().isNoContent());
    mockMvc.perform(get("/employees/1"))
        .andExpect(jsonPath("$.active").value(false));
  }

  @Test
  void should_update_when_update_employee_with_status_true() throws Exception {
    createEmployee("John Smith");
    String requestBody = """
                {
                    "name": "John Smith",
                    "age": 29,
                    "gender": "MALE",
                    "salary": 65000.0
                }
        """;

    mockMvc.perform(put("/employees/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.age").value(29))
        .andExpect(jsonPath("$.salary").value(65000.0))
        .andExpect(jsonPath("$.active").value(true));
  }
}
