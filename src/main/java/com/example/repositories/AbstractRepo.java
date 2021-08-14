package com.example.repositories;

import com.example.models.Company;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractRepo {

    // Interface to dynamodb

    public static final String COMPANY_NUMBER_COL = "number";
    public static final String COMPANY_NAME_COL = "name";

    // Name of the fields in the company are above
    // column names are defined by the fields

    public String getTableName(){
        return "Companies";
    }

    // NoSQL db requires a projection, define the fields to be returned

    protected ScanRequest scanRequest(){
        // dynamo db expects a ScanRequest
        return ScanRequest.builder().tableName(getTableName())
                .attributesToGet(COMPANY_NAME_COL, COMPANY_NUMBER_COL).build();
    }

    protected PutItemRequest putRequest(Company company) {
        // Put request takes a passed in company pojo
        // associates the names of the columns with the values
        Map<String, AttributeValue> item = new HashMap<>();
        item.put(COMPANY_NAME_COL, AttributeValue.builder().s(company.getName()).build());
        item.put(COMPANY_NUMBER_COL, AttributeValue.builder().s(company.getPhoneNumber()).build());

        return PutItemRequest.builder()
                .tableName(getTableName())
                .item(item)
                .build();
    }

    protected GetItemRequest getRequest(String number){
        Map<String, AttributeValue> key = new HashMap<>();
        key.put(COMPANY_NUMBER_COL, AttributeValue.builder().s(number).build());

        return GetItemRequest.builder()
                .tableName(getTableName())
                .key(key)
                .attributesToGet(COMPANY_NAME_COL, COMPANY_NUMBER_COL)
                .build();
    }


}
