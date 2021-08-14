package com.example.resources;


import com.example.models.Company;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/companies")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CompanyResource {

    @GET
    public List<Company> getAll(){
        return null;
        // use service to get movies
    }

    @POST
    public List<Company> add(Company company){
        // use service to add company
        return getAll();
    }

    @GET
    @Path("{number}")
    public Company getFromNumber(@PathParam("number") String number){
        // use service to return company based on number
        return null;
    }

}
