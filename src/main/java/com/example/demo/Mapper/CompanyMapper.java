package com.example.demo.Mapper;

import com.example.demo.dto.CompanyRequest;
import com.example.demo.dto.CompanyResponse;
import java.util.List;

import com.example.demo.entity.Company;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper {

  public CompanyResponse toCompanyResponse(com.example.demo.entity.Company company) {
    CompanyResponse companyResponse = new CompanyResponse();
    BeanUtils.copyProperties(company, companyResponse);
    return companyResponse;
  }

  public List<CompanyResponse> toCompanyResponses(List<com.example.demo.entity.Company> companies) {
    return companies.stream().map(this::toCompanyResponse).toList();
  }
  public Company toCompanyEntity(CompanyRequest companyRequest) {
    com.example.demo.entity.Company company = new Company();
    BeanUtils.copyProperties(companyRequest, company);
    return company;
  }

}
