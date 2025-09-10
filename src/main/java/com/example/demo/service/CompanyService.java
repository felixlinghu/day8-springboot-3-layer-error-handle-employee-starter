package com.example.demo.service;

import com.example.demo.entity.Company;
import com.example.demo.repository.CompanyRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {
@Autowired
  private CompanyRepository companyRepository;

  public void clear() {
    companyRepository.clear();
  }

  public List<Company> getCompanies(Integer page, Integer size) {
    List<Company> companies=companyRepository.getAllCompanies();
    if (page != null && size != null) {
      int start = (page - 1) * size;
      int end = Math.min(start + size, companies.size());
      if (start >= companies.size()) {
        return new ArrayList<>();
      }
      return companies.subList(start, end);
    }
    return companies;
  }

  public Company create(Company company) {
    return companyRepository.createCompany(company);
  }
}
