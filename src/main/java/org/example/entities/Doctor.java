package org.example.entities;

public class Doctor extends Person implements Trackable {

    private Integer id;
    private String specialty;

    private Doctor(Builder b) {
        super(b.firstName, b.lastName, b.age);
        this.id = b.id; this.specialty = b.specialty;
    }

    public static class Builder {
        private Integer id;
        private String firstName;
        private String lastName;
        private Integer age;
        private String email;
        private String specialty;

        public Builder setId(Integer id){ this.id = id; return this; }
        public Builder setFirstName(String v){ this.firstName = v; return this; }
        public Builder setLastName(String v){ this.lastName = v; return this; }
        public Builder setAge(Integer v){ this.age = v; return this; }
        public Builder setEmail(String v){ this.email = v; return this; }
        public Builder setSpecialty(String v){ this.specialty = v; return this; }

        public Doctor build(){ return new Doctor(this); }
    }

    @Override
    public void introduce() {
        System.out.println("Dr. " + getFirstName() + " " + getLastName() + " - " + specialty);
    }

    @Override
    public String trackingId() { return "Dr. - " + id; }

    public long getId(){ return id; }
    public String getSpecialty(){ return specialty; }

    @Override
    public String toString() {
        return "Doctor{" + id + ", " + getFirstName() + " " + getLastName() + ", spec =" + specialty + "}";
    }
}