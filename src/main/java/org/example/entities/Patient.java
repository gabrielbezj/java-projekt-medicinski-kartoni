package org.example.entities;

/**
 *  Predstavlja pacijenta.
 * <p>Nasljeđuje {@link Person} i dodaje podatke o OIB-u, broju osiguranika i spolu.</p>
 */
public class Patient extends Person implements Trackable {
    private String oib;
    private String insuranceId;
    private String gender;

    public Patient(String oib, String insuranceId, String firstName, String lastName, Integer age, String gender) {
        super(firstName, lastName, age);
        this.oib = oib;
        this.insuranceId = insuranceId;
        this.gender = gender;
    }

    @Override
    public void introduce() {
        System.out.println(getFirstName() + " " + getLastName() + " (" + gender + ")");
    }

    @Override
    public String trackingId() { return oib; }

    public String getOib() { return oib; }
    public String getInsuranceId() { return insuranceId; }
    public String getGender() { return gender; }

    @Override
    public String toString() {
        return "Patient{OIB ='" + oib + "', Broj osiguranika ='" + insuranceId + "', ime='" + getFirstName() + "', prezime='" + getLastName() +
                "', dob=" + getAge() + ", spol='" + gender + "'}";
    }
}

