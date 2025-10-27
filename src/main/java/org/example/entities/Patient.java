package org.example.entities;

public class Patient extends Person{

    private String gender;
    private String address;

    public Patient(String oib, String firstName, String lastName, Integer age, String email, String gender, String address) {
        super(oib, firstName, lastName, age, email);
        this.gender = gender;
        this.address = address;
    }

    @Override
    public void introduce() {
        System.out.println(getFirstName() + " " + getLastName() + " (" + gender + ")");
    }

    public String getGender() { return gender; }
    public String getAddress() { return address; }



    @Override
    public String toString() {
        return "Patient{OIB ='" + getOib() + "', ime='" + getFirstName() + "', prezime='" + getLastName() +
                "', dob=" + getAge() + ", spol='" + gender + "'}";
    }
}

