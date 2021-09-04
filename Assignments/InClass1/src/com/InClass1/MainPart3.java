package com.InClass1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

/*
 * InClass Assignment #1
 * MainPart3.java
 * Jared Tamulynas
 */
public class MainPart3 {
    /*
    * Question 3:
    * This is question you will use Data.users and Data.otherUsers arrays that include a list of users.
    * The goal is to print out the users that exist in both the Data.users and Data.otherUsers.
    * Two users are equal if all their attributes are equal.
    */

    public static void main(String[] args) {
        HashSet<User> users = new HashSet<>();

        for (String str : Data.users) {
            User user = new User(str);
            users.add(user);
        }

        for (String str : Data.otherUsers) {
            User user = new User(str);
            users.add(user);
        }

        ArrayList<User> usersList = new ArrayList<User>();

        for (User user : users) {
            usersList.add(user);
            System.out.println(user);
        }

        Collections.sort(usersList);
        System.out.println(user);

    }
}