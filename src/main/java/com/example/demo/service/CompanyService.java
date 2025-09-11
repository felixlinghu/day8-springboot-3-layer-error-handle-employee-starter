package com.example.demo.service;

import com.example.demo.entity.Company;
import com.example.demo.exception.InvalidCompanyIdException;
import com.example.demo.repository.CompanyRepositoryimpl;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

  private final CompanyRepositoryimpl companyRepository;

  public CompanyService(CompanyRepositoryimpl companyRepository) {
    this.companyRepository = companyRepository;
  }


  public List<Company> getCompanies(Integer page, Integer size) {
    if (page == null || size == null) {
      return companyRepository.findAll();
    } else {
      Pageable pageable = PageRequest.of(page - 1, size);
      return companyRepository.findAll(pageable).toList();
    }
  }

  public Company create(Company company) {
    return companyRepository.save(company);
  }

  public Company updateCompany(int id, Company updatedCompany) throws InvalidCompanyIdException {
    Company found = companyRepository.findById(id).orElse(null);
    if (found == null) {
      throw new InvalidCompanyIdException("Company not found with id: " + id);
    }
    updatedCompany.setId(id);
    return companyRepository.save(updatedCompany);
  }

  public Company getCompanyById(int id) throws InvalidCompanyIdException {
    Company found = companyRepository.findById(id).orElse(null);
    if (found == null) {
      throw new InvalidCompanyIdException("Company not found with id: " + id);
    }
    return found;
  }

  public void deleteCompany(int id) throws InvalidCompanyIdException {
    Company found = companyRepository.findById(id).orElse(null);
    if (found == null) {
      throw new InvalidCompanyIdException("Company not found with id: " + id);
    }
    companyRepository.delete(found);
  }
}
