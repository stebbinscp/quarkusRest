package com.example.services;

import com.example.models.Company;
import com.example.repositories.CompanyRepo;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class CompanyService {

    // business logic
    // currently simplistic
    // will be injected to resources

    @Inject
    CompanyRepo companyRepo;

    public List<Company> findAll(){
        //use repo to call findall
        return companyRepo.findAll();
    }

    public List<Company> add(Company company){
        return companyRepo.add(company);
    }

    public Company get(String number) {
        return companyRepo.get(number);
    }

}
