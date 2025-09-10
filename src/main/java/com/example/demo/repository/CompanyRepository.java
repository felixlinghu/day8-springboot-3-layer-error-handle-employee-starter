package com.example.demo.repository;

import com.example.demo.entity.Company;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class CompanyRepository {
  private final List<Company> companies = new ArrayList<>();

  public  void clear() {
    companies.clear();
  }

  public List<Company> getAllCompanies() {
    return companies;
  }
}
