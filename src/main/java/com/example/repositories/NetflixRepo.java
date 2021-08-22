package com.example.repositories;

import com.example.models.FavoriteNetflix;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import org.bson.types.ObjectId;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class NetflixRepo {

    MongoClient mongoClient = new MongoClient("localhost", 27017);
    MongoDatabase database = mongoClient.getDatabase("netflix");

    private boolean first = true;

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

    public String delete(String id){

        MongoCollection<Document> collection = database.getCollection("netflix");
        collection.deleteOne(new Document("_id", new ObjectId(id)));

        return "Deleted id"+id;
    }

    public String update(FavoriteNetflix favoriteNetflix, String id) {
        try {
            delete(id);
            add(favoriteNetflix);
            return "Updated!"+favoriteNetflix;
        } catch (Exception e){
            System.out.println(e);
            return "Item cannot updated as id does not exist";
        }
    }

    public FavoriteNetflix transform(String obj){

        System.out.println(obj);

        String[] parts = obj.split("\\{");
        String partsWanted = parts[2];
        String[] partsFromWanted = partsWanted.split(",");
//
        String[] idFromParts = partsFromWanted[0].split("="); //_id=6122dfa31062c87a6795f6c4
        String[] titleFromParts = partsFromWanted[1].split("="); //title=title,
        String[] synopsisFromParts = partsFromWanted[2].split("="); // synopsis=synopsis,
        String[] imgFromParts = partsFromWanted[1].split("\\}"); //img=https://picsum.photos/100/300}}

        String[] imgFromPartsNoBrackets = imgFromParts[0].split("="); //https://picsum.photos/100/300
//
        String id = idFromParts[1];
        String title = titleFromParts[1];
        String synopsis = synopsisFromParts[1];

        String img = imgFromPartsNoBrackets[1];
        FavoriteNetflix favoriteNetflix = new FavoriteNetflix();
        favoriteNetflix.setId(id);
        favoriteNetflix.setImg(img);
        favoriteNetflix.setTitle(title);
        favoriteNetflix.setSynopsis(synopsis);

        System.out.println(favoriteNetflix);

        return favoriteNetflix;
    }

}
