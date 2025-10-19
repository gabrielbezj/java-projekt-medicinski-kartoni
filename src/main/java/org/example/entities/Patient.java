package org.example.entities;

public class Patient {

    private String  oib;
    private String firstName;
    private String lastName;
    private int age;
    private String gender;
    private String address;
    private String email;
    private String phone;

    public Patient(String oib, String firstName, String lastName, int age, String gender) {
        this.oib = oib;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
    }
}

