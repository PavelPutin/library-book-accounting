package edu.vsu.putin_p_a.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Book {
    private int id;

    @Size(max = 512, message = "Name must be less than 512 characters.")
    @NotEmpty(message = "Name can't be empty.")
    private String name;

    @Size(max = 512, message = "Author name must be less than 512 characters.")
    @NotEmpty(message = "Author name can't be empty.")
    private String author;
    private Integer publishYear;
    private Integer ownerId;

    public Book(int id, String name, String author, Integer publishYear, Integer ownerId) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.publishYear = publishYear;
        this.ownerId = ownerId;
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

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }
}
