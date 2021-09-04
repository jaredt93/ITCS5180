package com.example.inclass03;

import java.io.Serializable;

// intents data passing using serializable objects
public class User implements Serializable {
    String name;
    int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public User() {

    }

}
