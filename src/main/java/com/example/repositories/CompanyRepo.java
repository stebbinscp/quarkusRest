package com.example.repositories;

import com.example.models.Company;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// switching databaes requires this to change, what repo is being called
// instantiated in the service

public class CompanyRepo extends AbstractRepo{

    @Inject
    DynamoDbClient dynamoDB;
    // marked to be injected later on
    // the repo should be alive for the life cycle of the app itself
    // while running, must have an instance of the repo

    public List<Company> findAll(){
        // clls scan paginator from dynamo and makes a scan request
        // based in abstract repo
        return dynamoDB.scanPaginator(scanRequest()).items().stream()
                // passing the item to the transform
                .map(i -> transform(i))
                .collect(Collectors.toList());
    }

    public List<Company> add(Company company){
        dynamoDB.putItem(putRequest(company));
        return findAll();
    }

    public Company get(String number){
        return transform(dynamoDB.getItem(getRequest(number)).item());
    }

    public Company transform(Map<String, AttributeValue> item){
        // take the map and transform to a movie
        // check for not null

        Company company = new Company();
        if (item != null && !item.isEmpty()){
            // set the values based on the items
            // all elements are strings, even numeric values
            // .s turns the item into a string
            company.setName(item.get(AbstractRepo.COMPANY_NAME_COL).s());
            company.setPhoneNumber(item.get(AbstractRepo.COMPANY_NUMBER_COL).s());
        }
        // n is a string representation of a number
        // see his example if we want to add numbers
        return company;
    }


}
