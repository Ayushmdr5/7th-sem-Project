package com.example.fortaxi;

public class User {
    public String name, email, numberPlate;
    public int reportCount;

    public User(){

    }

    public User(String name, String email, String numberPlate) {
        this.name = name;
        this.email = email;
        this.numberPlate = numberPlate;
        this.reportCount = 0;
    }
}
