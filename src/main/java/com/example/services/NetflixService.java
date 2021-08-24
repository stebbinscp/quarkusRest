package com.example.services;

import com.example.models.FavoriteNetflix;
import com.example.repositories.NetflixRepo;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class NetflixService {

    @Inject
    NetflixRepo netflixRepo;

    public List<FavoriteNetflix> findAll(){
        return netflixRepo.findAll();
    }

    public List<FavoriteNetflix> add(FavoriteNetflix favoriteNetflix){
        return netflixRepo.add(favoriteNetflix);
    }

    public void delete(String id){
        netflixRepo.delete(id);
    }

    public String update(FavoriteNetflix favoriteNetflix, String id){
        return netflixRepo.update(favoriteNetflix, id);
    }

}
