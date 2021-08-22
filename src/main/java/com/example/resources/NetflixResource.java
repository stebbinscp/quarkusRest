package com.example.resources;


import com.example.models.FavoriteNetflix;
import com.example.services.NetflixService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/netflix")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NetflixResource {

    @Inject
    NetflixService netflixService;

    @GET
    public List<FavoriteNetflix> getAll(){
        try {
            return netflixService.findAll();
        }
        catch (Exception e){
            throw new NotFoundException("No movies in database");
        }
    }

    @POST
    public List<FavoriteNetflix> add(FavoriteNetflix favoriteNetflix){
        netflixService.add(favoriteNetflix);
        return netflixService.findAll();
    }

    @DELETE
    @Path("{id}")
    public String delete(@PathParam("id") String id){
        try {
            return netflixService.delete(id);
        }
        catch (Exception e){
            throw new NotFoundException("No companies with that number in database");
        }
    }

    @PATCH
    @Path("{id}")
    public String update(@PathParam("id") String id, FavoriteNetflix favoriteNetflix) {
        try {
            return netflixService.update(favoriteNetflix, id);
        }
        catch (Exception e){
            throw new NotFoundException("No companies with that number in database");
        }
    }

}

