package com.example.models;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
// else when serialized, there will be no output
// now when compiled, include all reflection info for serialization
public class Company {

    private String name;
    private String number;

    @Override
    public String toString() {
        return "Company {" +
                "name='" + name + '\'' +
                ", phoneNumber=" + number +
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
