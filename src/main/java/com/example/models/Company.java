package com.example.models;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
// else when serialized, there will be no output
// now when compiled, include all reflection info for serialization
public class Company {

    private String name;
    private String phoneNumber;

    @Override
    public String toString() {
        return "Company {" +
                "name='" + name + '\'' +
                ", phoneNumber=" + phoneNumber +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
