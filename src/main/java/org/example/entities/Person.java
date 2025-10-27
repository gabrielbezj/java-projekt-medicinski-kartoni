package org.example.entities;

public abstract class Person {
    private String firstName;
    private String lastName;
    private Integer age;

    protected Person(String firstName, String lastName, Integer age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public String getFirstName() {return firstName;}
    public String getLastName() {return lastName;}
    public Integer getAge() {return age;}

    public abstract void introduce();
}
