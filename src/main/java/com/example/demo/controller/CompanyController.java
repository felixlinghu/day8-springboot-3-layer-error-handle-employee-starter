package com.example.demo.controller;

import com.example.demo.dto.CompanyResponse;
import com.example.demo.entity.Company;
import com.example.demo.exception.InvalidCompanyIdException;
import com.example.demo.service.CompanyService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/companies")
public class CompanyController {

  private final CompanyService companyService;


  public CompanyController(CompanyService companyService) {
    this.companyService = companyService;
  }


  @GetMapping
  public List<CompanyResponse> getCompanies(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
    return companyService.getCompanies(page, size);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CompanyResponse createCompany(@RequestBody Company company) {
    return companyService.create(company);

  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public CompanyResponse updateCompany(@PathVariable int id, @RequestBody Company updatedCompany) throws InvalidCompanyIdException {
    return companyService.updateCompany(id, updatedCompany);
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public CompanyResponse getCompanyById(@PathVariable int id) throws InvalidCompanyIdException {
    return companyService.getCompanyById(id);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteCompany(@PathVariable int id) throws InvalidCompanyIdException {
    companyService.deleteCompany(id);
  }
}
