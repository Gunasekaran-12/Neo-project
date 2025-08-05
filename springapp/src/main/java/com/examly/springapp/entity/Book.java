package com.examly.springapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "books", uniqueConstraints = @UniqueConstraint(columnNames = "isbn"))
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title can be at most 100 characters")
    private String title;
    
    @NotBlank(message = "Author is required")
    @Size(max = 50, message = "Author can be at most 50 characters")
    private String author;
    
    @NotBlank(message = "ISBN is required")
    @Size(min = 13, max = 13, message = "ISBN must be exactly 13 characters")
    private String isbn;
    
    @Min(value = 1000, message = "Publication year must be between 1000 and current year")
    @Max(value = 2025, message = "Publication year cannot exceed current year") // you may dynamically set this in service
    private int publicationYear;
    
    private boolean available = true; // by default book is available
    
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
    
    public int getPublicationYear() {
    return publicationYear;
    }
    
    public void setPublicationYear(int publicationYear) {
    this.publicationYear = publicationYear;
    }
    
    public boolean isAvailable() {
    return available;
    }
    
    public void setAvailable(boolean available) {
    this.available = available;
    }
}