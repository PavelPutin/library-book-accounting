package edu.vsu.putin_p_a.models;

import jakarta.validation.constraints.Size;

public class Person {
    private int id;

    @Size(min = 2, max = 512, message = "Full name length must be between 2 and 512 include.")
    private String fullName;

    private int birthdayYear;

    public Person(int id, String fullName, int birthdayYear) {
        this.id = id;
        this.fullName = fullName;
        this.birthdayYear = birthdayYear;
    }

    public Person() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getBirthdayYear() {
        return birthdayYear;
    }

    public void setBirthdayYear(int birthdayYear) {
        this.birthdayYear = birthdayYear;
    }
}
