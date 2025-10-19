package org.example.entities;

public class Record {
    private Long id;
    private Patient patient;
    private Doctor doctor;
    private Item[] items;


    public Record(Long id, Patient patient, Doctor doctor, Item[] items) {
        this.id = id;
        this.patient = patient;
        this.doctor = doctor;
        this.items = items;
    }
}
