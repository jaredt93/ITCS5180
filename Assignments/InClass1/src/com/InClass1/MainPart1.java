package com.InClass1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/*
 * InClass Assignment #1
 * MainPart1.java
 * Jared Tamulynas
 */
public class MainPart1 {
    /*
    * Question 1:
    * - In this question you will use the Data.users array that includes
    * a list of users. Formatted as : firstname,lastname,age,email,gender,city,state
    * - Create a User class that should parse all the parameters for each user.
    * - Insert each of the users in a list.
    * - Print out the TOP 10 oldest users.
    * */

    public static void main(String[] args) {
        ArrayList<User> users = new ArrayList<User>();

        for (String str : Data.users) {
            User user = new User(str);
            users.add(user);
        }

        Collections.sort(users);

        for (int i = 0; i < Math.min(10, users.size()); i++) {
            System.out.println(users.get(i));
        }
    }
}