package org.example.entities;

public class Booking {
    private Long id;
    private Patient patient;
    private Doctor doctor;
    private String dateTime;

    public Booking(Long id, Patient patient, Doctor doctor, String dateTime) {
        this.id = id;
        this.patient = patient;
        this.doctor = doctor;
        this.dateTime = dateTime;
    }
}
