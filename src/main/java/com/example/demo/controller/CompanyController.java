package com.example.demo.controller;

import com.example.demo.entity.Company;
import com.example.demo.exception.InvalidCompanyIdException;
import com.example.demo.exception.InvalidDataMessageException;
import com.example.demo.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

  private final CompanyService companyService;


  public CompanyController(CompanyService companyService) {
    this.companyService = companyService;
  }

  @DeleteMapping("/clear")
  public void clear() {
    companyService.clear();
  }

  @GetMapping
  public List<Company> getCompanies(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
    return companyService.getCompanies(page, size);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Company createCompany(@RequestBody Company company) {
    return companyService.create(company);

  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Company updateCompany(@PathVariable int id, @RequestBody Company updatedCompany) throws InvalidCompanyIdException {
    return companyService.updateCompany(id, updatedCompany);
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Company getCompanyById(@PathVariable int id) throws InvalidCompanyIdException {
    return companyService.getCompanyById(id);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteCompany(@PathVariable int id) throws InvalidCompanyIdException {
    companyService.deleteCompany(id);
  }
}
