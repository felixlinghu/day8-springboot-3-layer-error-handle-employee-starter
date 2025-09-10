package com.example.demo.repository;

import com.example.demo.entity.Company;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class CompanyRepository {
  private final List<Company> companies = new ArrayList<>();
private int id=1;
  public  void clear() {
    companies.clear();
  }

  public List<Company> getAllCompanies() {
    return companies;
  }

  public Company createCompany(Company company) {
    company.setId(id++);
    companies.add(company);
    return company;
  }

  public Company getCompanyById(int id) {
    return companies.stream().filter(company -> company.getId()==id).findFirst().orElse(null);
  }

  public Company updateCompany(Company originCompany, Company updatedCompany) {
    return updatedCompany;
  }

  public void deleteCompany(Company found) {
    companies.remove(found);
  }
}
