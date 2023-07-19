package edu.vsu.putin_p_a.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;

public class Book {
    private int id;

    @Max(value = 512, message = "Name must be less than 512 characters.")
    @NotEmpty(message = "Name can't be empty.")
    private String name;

    @Max(value = 512, message = "Author name must be less than 512 characters.")
    @NotEmpty(message = "Author name can't be empty.")
    private String author;
    private Integer publishYear;
    private Integer owner_id;

    public Book(int id, String name, String author, Integer publishYear, Integer owner_id) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.publishYear = publishYear;
        this.owner_id = owner_id;
    }

    public Book() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(Integer publishYear) {
        this.publishYear = publishYear;
    }

    public Integer getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(Integer owner_id) {
        this.owner_id = owner_id;
    }
}
