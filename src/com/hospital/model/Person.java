package com.hospital.model;

/**
 * Abstract base class representing a generic Person in the Hospital Management System.
 * Demonstrates OOP Abstraction and Encapsulation.
 */
public abstract class Person {
    protected String id;
    protected String name;
    protected int age;
    protected String gender;
    protected String contactNumber;

    public Person(String id, String name, int age, String gender, String contactNumber) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.contactNumber = contactNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    /**
     * Abstract method to be implemented by concrete subclasses.
     * Demonstrates Polymorphism.
     */
    public abstract String getRoleDetails();

    @Override
    public String toString() {
        return String.format("ID: %-8s | Name: %-18s | Age: %-3d | Gender: %-6s | Phone: %s",
                id, name, age, gender, contactNumber);
    }
}
