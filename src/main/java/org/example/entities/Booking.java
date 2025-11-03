package org.example.entities;

/**
 * Predstavlja rezervaciju termina između pacijenta i doktora.
 * <p>Implementira sučelja {@link Reservable} i {@link Trackable}.</p>
 */


import java.io.Serializable;

public non-sealed class Booking implements Reservable, Trackable {
    private final Long id;
    private final Patient patient;
    private final Doctor doctor;
    private final String dateTime;
    private boolean reserved;

    public Booking(Long id, Patient patient, Doctor doctor, String dateTime) {
        this.id = id;
        this.patient = patient;
        this.doctor = doctor;
        this.dateTime = dateTime;
    }

    public Long getId(){ return id; }
    public Patient getPatient(){ return patient; }
    public Doctor getDoctor(){ return doctor; }
    public String getDateTime(){ return dateTime; }

    @Override
    public boolean reserve() { reserved = true; return true; }

    @Override
    public boolean cancel()  { reserved = false; return true; }

    @Override
    public String trackingId() { return "BOOK-" + id; }

    @Override
    public String toString(){
        return "Booking{" + id + ", " + dateTime + ", " + patient + ", " + doctor + ", reserved=" + reserved + "}";
    }
}
