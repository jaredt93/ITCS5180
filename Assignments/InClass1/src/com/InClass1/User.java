package com.InClass1;

/*
 * InClass Assignment #1
 * User.java
 * Jared Tamulynas
 */
public class User implements Comparable<User> {
    String firstName;
    String lastName;
    String email;
    String gender;
    String city;
    String state;
    int age;

    public User(String firstName, String lastName, String email, String gender, String city, String state, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.city = city;
        this.state = state;
        this.age = age;
    }

    public User(String line) {
        String[] items = line.split(",");
        this.firstName = items[0];
        this.lastName = items[1];

        try {
            this.age = Integer.parseInt(items[2]);
        } catch (NumberFormatException ex) {
            throw new NumberFormatException("Invalid age.");
        }

        this.email = items[3];
        this.gender= items[4];
        this.city = items[5];
        this.state = items[6];
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public int compareTo(User o) {
        return -1 * (this.age - o.getAge());
    }
}
