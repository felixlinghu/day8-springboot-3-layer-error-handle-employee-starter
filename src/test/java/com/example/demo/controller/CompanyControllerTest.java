package com.example.demo.controller;

import com.example.demo.entity.Company;
import com.example.demo.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyControllerTest {

  @Autowired
  private MockMvc mockMvc;


  @BeforeEach
  void cleanCompanies() throws Exception {
    mockMvc.perform(delete("/companies/clear"));
  }

  @Test
  void should_return_created_company_when_post_companies() throws Exception {
    String requestBody = """
        {
            "name": "Spring"
        }
        """;
    mockMvc.perform(post("/companies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("Spring"));
  }

  @Test
  void should_return_all_companies_when_no_param() throws Exception {
    String requestBody = """
        {
            "name": "Spring"
        }
        """;
    mockMvc.perform(post("/companies")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBody));
    mockMvc.perform(get("/companies").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(1));
  }

  @Test
  void should_return_company_when_get_id_found() throws Exception {
    String requestBody = """
        {
            "name": "Spring"
        }
        """;
    mockMvc.perform(post("/companies")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBody));
    MockHttpServletRequestBuilder request = get("/companies/" + 1)
        .contentType(MediaType.APPLICATION_JSON);
    mockMvc.perform(request)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("Spring"));
  }

  @Test
  void should_return_company_when_put_with_id_found() throws Exception {
    String requestBody = """
        {
            "name": "Spring"
        }
        """;
    mockMvc.perform(post("/companies")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBody));
    String requestBody1 = """
        {
        "id":1,
            "name": "Spring2"
        }
        """;
    mockMvc.perform(put("/companies/" + 1)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody1))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("Spring2"));
  }

  @Test
  void should_return_no_content_when_delete_id_found() throws Exception {
    String requestBody = """
        {
            "name": "Spring"
        }
        """;
    mockMvc.perform(post("/companies")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBody));
    MockHttpServletRequestBuilder request = delete("/companies/" + 1)
        .contentType(MediaType.APPLICATION_JSON);

    mockMvc.perform(request)
        .andExpect(status().isNoContent());
  }

  @Test
  void should_return_truncated_companies_when_page_size_is_limit() throws Exception {
    String requestBody = """
        {
            "name": "Spring"
        }
        """;
    for (int i = 0; i < 10; i++) {
      MockHttpServletRequestBuilder postRequest = post("/companies")
          .contentType(MediaType.APPLICATION_JSON)
          .content(requestBody);
      mockMvc.perform(postRequest); // Ensure each company is created
    }
    MockHttpServletRequestBuilder request = get("/companies?page=1&size=5")
        .contentType(MediaType.APPLICATION_JSON);

    mockMvc.perform(request)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(5));
  }

  @Test
  void should_status_404_when_get_company_by_id_not_found() throws Exception {
    MockHttpServletRequestBuilder request = get("/companies/999")
        .contentType(MediaType.APPLICATION_JSON);

    mockMvc.perform(request)
        .andExpect(status().isNotFound());
  }

  @Test
  void should_status_404_when_put_company_by_id_not_found() throws Exception {
    String requestBody = """
        {
            "name": "Spring2"
        }
        """;
    MockHttpServletRequestBuilder request = put("/companies/999")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBody);

    mockMvc.perform(request)
        .andExpect(status().isNotFound());
  }
}
