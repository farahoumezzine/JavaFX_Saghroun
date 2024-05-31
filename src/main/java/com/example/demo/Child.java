package com.example.demo;

import java.time.LocalDate;

public class Child {
    private long id;
    private String username;
    private String email;
    private String phone;
    private String password;
    private String passwordConfirmation;
    private String firstname;
    private String lastname;
    private LocalDate dateOfBirth;
    private String activities;
    private String filename;
    private String gender;
    private String allergies;
    private String conditions;
    private String message;
    // Assuming you don't need 'created_at', 'updated_at', and 'deleted_at' fields in the Java class

    // Constructors, getters, and setters
    // Constructor
    public Child(long id, String username, String email, String phone, String password, String passwordConfirmation,
                 String firstname, String lastname, LocalDate dateOfBirth, String activities, String filename,
                 String gender, String allergies, String conditions, String message) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
        this.firstname = firstname;
        this.lastname = lastname;
        this.dateOfBirth = dateOfBirth;
        this.activities = activities;
        this.filename = filename;
        this.gender = gender;
        this.allergies = allergies;
        this.conditions = conditions;
        this.message = message;
    }

    public Child(String username, String email, String phone, String password, String passwordConfirmation,
                 String firstname, String lastname, LocalDate dateOfBirth, String activities, String filename,
                 String gender, String allergies, String conditions, String message) {
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
        this.firstname = firstname;
        this.lastname = lastname;
        this.dateOfBirth = dateOfBirth;
        this.activities = activities;
        this.filename = filename;
        this.gender = gender;
        this.allergies = allergies;
        this.conditions = conditions;
        this.message = message;
    }


    public Child() {

    }

    // Getters and setters
    // Add getters and setters for each field

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getActivities() {
        return activities;
    }

    public void setActivities(String activities) {
        this.activities = activities;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
