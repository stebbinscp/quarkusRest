package com.example.repositories;

import com.example.models.Company;
import com.github.javafaker.Faker;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.apache.commons.lang3.ObjectUtils;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

// switching databaes requires this to change, what repo is being called
// instantiated in the service

@ApplicationScoped
public class CompanyRepo{

//    @Inject
    MongoClient mongoClient = new MongoClient("localhost", 27017);
    MongoDatabase database = mongoClient.getDatabase("companies");
    // marked to be injected later

    private boolean first = true;

    public List<Company> findAll(){

        if(first){
            populate();
            first = false;
        }

        MongoCollection<Document> collection = database.getCollection("companies");
        MongoCursor<Document> cursor = collection.find().iterator();
        List<Company> results = new ArrayList<>();

        while(cursor.hasNext()) {
            String obj = cursor.next().toString();
            results.add(transform(obj));
        }
        return results;

    }

    public List<Company> add(Company company){

        if(first){
            populate();
            first = false;
        }

        Document doc = new Document();
        doc.append("name",company.getName());
        doc.append("number", company.getPhoneNumber());
        database.getCollection("companies").insertOne(doc);
        return findAll();
    }

    public Company get(String number){

        if(first){
            populate();
            first = false;
        }
        MongoCollection<Document> collection = database.getCollection("companies");
        MongoCursor<Document> cursor = collection.find(eq("number", number)).iterator();
        List<Company> results = new ArrayList<>();
        while(cursor.hasNext()) {
            String obj = cursor.next().toString();
            results.add(transform(obj));
        }

        try {
            Company result = results.get(0);
            return result;
        }
        catch (Exception e){
            return null;
        }

    }

    public String delete(String id){

        MongoCollection<Document> collection = database.getCollection("companies");
        collection.deleteOne(new Document("_id", new ObjectId(id)));

        return "Deleted id"+id;
    }

    public String update(Company company, String id) {
        MongoCollection<Document> collection = database.getCollection("companies");
        collection.updateOne(eq("_id", id), Updates.set("name", company.getName()));
        collection.updateOne(eq("_id", id), Updates.set("number", company.getPhoneNumber()));

        Document myDoc = collection.find(eq("_id", id)).first();

        if (myDoc == null){
            return "no such id exists in the database";
        } else {
            return "updated id "+id+" to given info "+company;
        }
    }

    public Company transform(String obj){

        String[] parts = obj.split("\\{");
        String partsWanted = parts[2];
        String[] partsFromWanted = partsWanted.split(",");

        String[] idFromParts = partsFromWanted[0].split("=");
        String[] nameFromParts = partsFromWanted[1].split("=");
        String[] numberFromParts = partsFromWanted[2].split("=");
        String[] numberFromPartsNoBracket = numberFromParts[1].split("\\}");

        String id = idFromParts[1];
        String name = nameFromParts[1];
        String number = numberFromPartsNoBracket[0];
        Company company = new Company();
        company.setPhoneNumber(number);
        company.setName(name);
        company.setId(id);

        return company;
    }

    public void populate() {
        List<Document> list = new ArrayList<Document>();
        Faker faker = new Faker();
        for (int i = 1; i <= 1000; i++) {
            String name = faker.name().firstName();
            String number = faker.phoneNumber().phoneNumber().toString();
            Document doc = new Document();
            doc.append("name",name);
            doc.append("number",number);
            list.add(doc);
        }
        database.getCollection("companies").insertMany(list);
    }

}
