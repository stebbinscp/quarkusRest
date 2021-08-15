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
        return companyService.findAll();
        // use service to get movies
    }

    @POST
    public List<Company> add(Company company){
        // use service to add company
        System.out.println(company);
        companyService.add(company);
        return companyService.findAll();
    }

    @GET
    @Path("{number}")
    public Company getFromNumber(@PathParam("number") String number){
        // use service to return company based on number
        return companyService.get(number);
    }

}

// need a dynamo db to be running locally for this to work
