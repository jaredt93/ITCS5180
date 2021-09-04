package com.InClass1;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/*
 * InClass Assignment #1
 * MainPart2.java
 * Jared Tamulynas
 */
public class MainPart2 {
    /*
    * Question 2:
    * - In this question you will use the Data.users array that includes
    * a list of users. Formatted as : firstname,lastname,age,email,gender,city,state
    * - Create a User class that should parse all the parameters for each user.
    * - The goal is to count the number of users living each state.
    * - Print out the list of State, Count order in ascending order by count.
    * */

    public static void main(String[] args) {
        HashMap<String, StateCount> stateMap = new HashMap<>();

        for (String str : Data.users) {
            User user = new User(str);
            String state = user.getState();

            if(stateMap.containsKey(state)) {
                StateCount stateCount = stateMap.get(state);
                stateCount.increment();
                stateMap.put(state, stateCount);
            } else {
                stateMap.put(state, new StateCount(state, 1));
            }
        }

        ArrayList<StateCount> stateCounts = new ArrayList<>(stateMap.values());
        stateCounts.add();

        Collections.sort(stateCounts, Comparator<StateCount>) {
            public int compare(StateCount o1, StateCount o2) {
                return o2.count - o1.count;
            }
        });


        for (StateCount s: stateCounts) {
            System.out.println(s);
        }
    }
}