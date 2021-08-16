package com.example.repositories;

import com.example.models.Company;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import io.vertx.core.json.JsonObject;
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
//    DynamoDbClient dynamoDB;
    // marked to be injected later on
    // the repo should be alive for the life cycle of the app itself
    // while running, must have an instance of the repo

    public List<Company> findAll(){

        MongoCollection<Document> collection = database.getCollection("companies");
        MongoCursor<Document> cursor = collection.find().iterator();
        FindIterable<Document> iterable = collection.find();
        List<Company> results = new ArrayList<>();

        while(cursor.hasNext()) {
            String obj = cursor.next().toString();
            String[] parts = obj.split("\\{");
            String partsWanted = parts[2];
//            System.out.println(partsWanted);
            String[] partsFromWanted = partsWanted.split(",");
//            System.out.println(partsFromWanted[0]);
//            System.out.println(partsFromWanted[1]);
//            System.out.println(partsFromWanted[2]);

            String[] idFromParts = partsFromWanted[0].split("=");
            String[] nameFromParts = partsFromWanted[1].split("=");
            String[] numberFromParts = partsFromWanted[2].split("=");
            String[] numberFromPartsNoBracket = numberFromParts[1].split("\\}");

            String id = idFromParts[1]; //works
            String name = nameFromParts[1];
            String number = numberFromPartsNoBracket[0];
//            System.out.println(name);
//            System.out.println(number);
            Company company = new Company();
            company.setPhoneNumber(number);
            company.setName(name);
            results.add(company);

        }
        return results;

    }

    public List<Company> add(Company company){
        return null;
//        System.out.println(company);
//        dynamoDB.putItem(putRequest(company));
//        return findAll();
    }

    public Company get(String number){
        return null;
//        return transform(dynamoDB.getItem(getRequest(number)).item());
    }

//    public Company transform(Map<String, AttributeValue> item){
//        // take the map and transform to a movie
//        // check for not null
//
//        Company company = new Company();
//        if (item != null && !item.isEmpty()){
//            // set the values based on the items
//            // all elements are strings, even numeric values
//            // .s turns the item into a string
//            company.setName(item.get(AbstractRepo.COMPANY_NAME_COL).s());
//            company.setPhoneNumber(item.get(AbstractRepo.COMPANY_NUMBER_COL).s());
//        }
//        // n is a string representation of a number
//        // see his example if we want to add numbers
//        return company;
//    }


}
