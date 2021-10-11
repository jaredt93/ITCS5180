package com.example.group20_inclass07;

import java.io.Serializable;

// Contact class
public class Contact implements Serializable {

    String name;
    String email;
    String phoneNumber;
    String type;

    public Contact() {
        // emplty constructor
    }

    public Contact(String name, String email, String phoneNumber, String type) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
