package com.example.myapplication;

import java.io.Serializable;

public class Person implements Serializable {
    private String name;
    private int imageResource;
    private int riskFactor;
    private int age;
    private String location;
    private String pastCrimes;

    public Person(String name, int imageResource, int riskFactor, int age, String location, String pastCrimes) {
        this.name = name;
        this.imageResource = imageResource;
        this.riskFactor = riskFactor;
        this.age = age;
        this.location = location;
        this.pastCrimes = pastCrimes;
    }

    public String getName() {
        return name;
    }

    public int getImageResource() {
        return imageResource;
    }

    public int getRiskFactor() {
        return riskFactor;
    }

    public int getAge() {
        return age;
    }

    public String getLocation() {
        return location;
    }

    public String getPastCrimes() {
        return pastCrimes;
    }
}
