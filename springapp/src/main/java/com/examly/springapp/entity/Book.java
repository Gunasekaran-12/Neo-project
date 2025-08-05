package com.examly.springapp.entity;

import java.util.function.BooleanSupplier;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import jakarta.persistence.GeneratedValue;

@Entity
public class Book {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Title is required")
    private String title;
    
    @NotBlank(message = "Author is required")
    private String author;
    
    @NotBlank(message = "ISBN is required")
    @Size(min = 10, max = 13, message = "ISBN must be 10-13 characters")
    private String isbn;
    
    @NotNull(message = "Publication year is required")
    private Integer publicationYear;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public void setAvailable(boolean b) {
        throw new UnsupportedOperationException("Unimplemented method 'setAvailable'");
    }

    public BooleanSupplier getAvailable() {
        throw new UnsupportedOperationException("Unimplemented method 'getAvailable'");
    }
}