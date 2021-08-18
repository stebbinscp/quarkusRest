package com.example.models;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
// else when serialized, there will be no output
// now when compiled, include all reflection info for serialization
public class Company {

    private String name;
    private String number;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return number;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.number = phoneNumber;
    }
}
