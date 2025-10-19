package org.example.entities;

public class Doctor {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String specialty;

    public Doctor(Integer id, String firstName, String lastName, String email, String specialty) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.specialty = specialty;
    }
}
