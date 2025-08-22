package com.examly.springapp.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;


@Entity
public class Book {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Title is required")
    @Column(nullable = false)
    private String title;
    
    @NotBlank(message = "Author is required")
    @Column(nullable = false)
    private String author;
    
    @NotBlank(message = "ISBN is required")
    @Column(nullable = false, unique = true)
    @Pattern(regexp = "\\d{10}|\\d{13}", message = "ISBN must be 10 or 13 digits")
    private String isbn;
    
    @Min(value = 1900, message = "Publication year must be after 1900")
    @Max(value = 2100, message = "Publication year must be before 2100")
    private Integer publicationYear;
    
    private Boolean available = true;
    
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BorrowRecord> borrowRecords = new ArrayList<>();

    // Constructors
    public Book() {}
    public Book(String title, String author, String isbn, Integer publicationYear) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public Integer getPublicationYear() { return publicationYear; }
    public void setPublicationYear(Integer publicationYear) { this.publicationYear = publicationYear; }
    public Boolean getAvailable() { return available; }
    public void setAvailable(Boolean available) { this.available = available; }
    public List<BorrowRecord> getBorrowRecords() { return borrowRecords; }
    public void setBorrowRecords(List<BorrowRecord> borrowRecords) { this.borrowRecords = borrowRecords; }
}