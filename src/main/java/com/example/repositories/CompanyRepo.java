package com.example.repositories;

import com.example.models.Company;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import io.vertx.core.json.JsonObject;
import static com.mongodb.client.model.Filters.eq;
import org.bson.Document;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// switching databaes requires this to change, what repo is being called
// instantiated in the service

@ApplicationScoped
public class CompanyRepo extends AbstractRepo{

//    @Inject
    MongoClient mongoClient = new MongoClient("localhost", 27017);
    MongoDatabase database = mongoClient.getDatabase("companies");
    // marked to be injected later on

    public List<Company> findAll(){

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

        Document doc = new Document();
        doc.append("name",company.getName());
        doc.append("number", company.getPhoneNumber());
        database.getCollection("companies").insertOne(doc);
        return findAll();
    }

    public Company get(String number){
        MongoCollection<Document> collection = database.getCollection("companies");
        MongoCursor<Document> cursor = collection.find(eq("number", number)).iterator();
        List<Company> results = new ArrayList<>();
        while(cursor.hasNext()) {
            String obj = cursor.next().toString();
            results.add(transform(obj));
        }
        return results.get(0);
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

        return company;
    }

}
