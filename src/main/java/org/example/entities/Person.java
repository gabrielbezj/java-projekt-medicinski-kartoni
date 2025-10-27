package org.example.entities;

public abstract class Person {
    private String  oib;
    private String firstName;
    private String lastName;
    private Integer age;
    private String email;

    protected Person(String  oib, String firstName, String lastName, Integer age, String email) {
        this.oib = oib;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
    }

    public String getOib() {return oib;}
    public String getFirstName() {return firstName;}
    public String getLastName() {return lastName;}
    public Integer getAge() {return age;}
    public String getEmail() {return email;}

    public abstract void introduce();
}
