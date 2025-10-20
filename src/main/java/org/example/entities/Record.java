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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Item[] getItems() {
        return items;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }
}
