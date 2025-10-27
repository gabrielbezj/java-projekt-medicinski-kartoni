package org.example.entities;

public class Record {
    private Long id;
    private Patient patient;
    private Doctor doctor;
    private Item[] items;


    public Record(long id, Patient patient, Doctor doctor, Item[] items) {
        this.id = id; this.patient = patient; this.doctor = doctor; this.items = items;
    }

    public long getId(){ return id; }
    public Patient getPatient(){ return patient; }
    public Doctor getDoctor(){ return doctor; }
    public Item[] getItems(){ return items; }

    @Override
    public String toString(){
        return "Record{" + id + ", patient =" + patient + ", doctor =" + doctor +
                ", items =" + (items==null ? 0 : items.length) + "}";
    }
}
