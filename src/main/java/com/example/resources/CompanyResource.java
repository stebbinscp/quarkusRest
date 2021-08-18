package com.example.resources;


import com.example.models.Company;
import com.example.services.CompanyService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/companies")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CompanyResource {

    @Inject
    CompanyService companyService;

    @GET
    public List<Company> getAll(){
        try {
            return companyService.findAll();
        }
        catch (Exception e){
            throw new NotFoundException("No companies in database");
        }
        // use service to get movies
    }

    @POST
    public List<Company> add(Company company){
        // use service to add company
//        System.out.println(company);
        companyService.add(company);
        return companyService.findAll();
    }

    @GET
    @Path("{number}")
    public Company getFromNumber(@PathParam("number") String number){
        // use service to return company based on number
        System.out.println(companyService.get(number));
        if (null == companyService.get(number)) {
            throw new NotFoundException("No companies with that number in database");
        } else {
            return companyService.get(number);
        }
    }

    @DELETE
    @Path("{id}")
    public String delete(@PathParam("id") String id){
        // use service to return company based on number
        try {
            return companyService.delete(id);
        }
        catch (Exception e){
            throw new NotFoundException("No companies with that number in database");
        }
    }

    @PATCH
    @Path("{id}")
    public String update(@PathParam("id") String id, Company company) {
        try {
            return companyService.update(company, id);
        }
        catch (Exception e){
            throw new NotFoundException("No companies with that number in database");
        }
    }

}

