package com.example.repositories;

import com.example.models.FavoriteNetflix;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import org.bson.types.ObjectId;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class NetflixRepo {

//    @Inject
//    MongoClient mongoClient;

    MongoClient mongoClient = new MongoClient("localhost", 27017);
    MongoDatabase database = mongoClient.getDatabase("netflix");

    public List<FavoriteNetflix> findAll(){

        MongoCollection<Document> collection = database.getCollection("netflix");
        MongoCursor<Document> cursor = collection.find().iterator();
        List<FavoriteNetflix> results = new ArrayList<>();

        while(cursor.hasNext()) {
            String obj = cursor.next().toString();
            results.add(transform(obj));
        }
        return results;

    }

    public List<FavoriteNetflix> add(FavoriteNetflix favoriteNetflix){

        Document doc = new Document();
        doc.append("title", favoriteNetflix.getTitle());
        doc.append("synopsis", favoriteNetflix.getSynopsis());
        doc.append("img", favoriteNetflix.getImg());
        database.getCollection("netflix").insertOne(doc);
        return findAll();
    }

    public void delete(String id){

        MongoCollection<Document> collection = database.getCollection("netflix");
        collection.deleteOne(new Document("_id", new ObjectId(id)));

    }

    public void update(FavoriteNetflix favoriteNetflix, String id) {
        try {
            delete(id);
            add(favoriteNetflix);
        } catch (Exception e){
            System.out.println(e);
        }
    }

    public FavoriteNetflix transform(String obj){

        String[] parts = obj.split("\\{");
        String partsWanted = parts[2];
        String[] partsFromWanted = partsWanted.split("=");

        String id = partsFromWanted[1].replace(", title", "");
        String title = partsFromWanted[2].replace(", synopsis", "");
        String synopsis = partsFromWanted[3].replace(", img", "");
        String img = partsFromWanted[4];

        FavoriteNetflix favoriteNetflix = new FavoriteNetflix();
        favoriteNetflix.setId(id);
        favoriteNetflix.setImg(img);
        favoriteNetflix.setTitle(title);
        favoriteNetflix.setSynopsis(synopsis);

        return favoriteNetflix;
    }

}
