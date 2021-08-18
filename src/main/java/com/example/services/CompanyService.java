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

    public String delete(String id){
        return companyRepo.delete(id);
    }

    public String update(Company company, String id){
        return companyRepo.update(company, id);
    }

    public Company get(String number) {
        System.out.println(companyRepo.get(number));
        return companyRepo.get(number);
    }

}
