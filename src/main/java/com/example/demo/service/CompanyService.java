package com.example.demo.service;

import com.example.demo.Mapper.CompanyMapper;
import com.example.demo.dto.CompanyResponse;
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
private final CompanyMapper companyMapper;
  public CompanyService(CompanyRepositoryimpl companyRepository,  CompanyMapper companyMapper) {
    this.companyRepository = companyRepository;
    this.companyMapper = companyMapper;
  }


  public List<CompanyResponse> getCompanies(Integer page, Integer size) {
    if (page == null || size == null) {
      return companyMapper.toCompanyResponses(companyRepository.findAll());
    } else {
      Pageable pageable = PageRequest.of(page - 1, size);
      return companyMapper.toCompanyResponses(companyRepository.findAll(pageable).toList());
    }
  }

  public CompanyResponse create(Company company) {
    return companyMapper.toCompanyResponse(companyRepository.save(company));
  }

  public CompanyResponse updateCompany(int id, Company updatedCompany) throws InvalidCompanyIdException {
    Company found = companyRepository.findById(id).orElse(null);
    if (found == null) {
      throw new InvalidCompanyIdException("Company not found with id: " + id);
    }
    updatedCompany.setId(id);
    return companyMapper.toCompanyResponse(companyRepository.save(updatedCompany));
  }

  public CompanyResponse getCompanyById(int id) throws InvalidCompanyIdException {
    Company found = companyRepository.findById(id).orElse(null);
    if (found == null) {
      throw new InvalidCompanyIdException("Company not found with id: " + id);
    }
    return companyMapper.toCompanyResponse(found);
  }

  public void deleteCompany(int id) throws InvalidCompanyIdException {
    Company found = companyRepository.findById(id).orElse(null);
    if (found == null) {
      throw new InvalidCompanyIdException("Company not found with id: " + id);
    }
    companyRepository.delete(found);
  }
}
