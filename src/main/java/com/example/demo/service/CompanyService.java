package com.example.demo.service;

import com.example.demo.entity.Company;
import com.example.demo.exception.InvalidCompanyIdException;
import com.example.demo.exception.InvalidDataMessageException;
import com.example.demo.repository.CompanyRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CompanyService {

  private final CompanyRepository companyRepository;

  public CompanyService(CompanyRepository companyRepository) {
    this.companyRepository = companyRepository;
  }

  public void clear() {
    companyRepository.clear();
  }

  public List<Company> getCompanies(Integer page, Integer size) {
    List<Company> companies = companyRepository.getAllCompanies();
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

  public Company updateCompany(int id, Company updatedCompany) throws InvalidCompanyIdException {
    Company found = companyRepository.getCompanyById(id);
    if (found == null) {
      throw new InvalidCompanyIdException("Company not found with id: " + id);
    }
    return companyRepository.updateCompany(found, updatedCompany);
  }

  public Company getCompanyById(int id) throws InvalidCompanyIdException {
    Company found = companyRepository.getCompanyById(id);
    if (found == null) {
      throw new InvalidCompanyIdException("Company not found with id: " + id);
    }
    return found;
  }

  public void deleteCompany(int id) throws  InvalidCompanyIdException {
    Company found = companyRepository.getCompanyById(id);
    if (found == null) {
      throw new InvalidCompanyIdException("Company not found with id: " + id);
    }
    companyRepository.deleteCompany(found);
  }
}
