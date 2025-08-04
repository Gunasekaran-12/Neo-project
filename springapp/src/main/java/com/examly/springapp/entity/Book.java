package com.examly.springapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "books", uniqueConstraints = @UniqueConstraint(columnNames = "isbn"))
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank( message = "Title is required")
    @Size(max = 100, message = "Title Can't be at most 100 Characters" )
    private String title;
    
    @NotBlank(message = "Author is Required")
    @Size(max = 50, message = "Author Can't be at most 50 Characters" )
    private String author;
    
    @NotBlank(message = "ISBN is Required")
    @Size( min = 13,max = 13, message = "ISBN must be 13 Characters" )
    private String isbn;

    @Min(value = 1000, message = "Publication year must be between 1000 and current year")
    @Min(value = 1000, message = "Publication year can't exceed current year")
    private int publicationYear;

    private boolean available = true;

    // Getters and Setters

    public Long getId(){ 
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getAuhtor(){
        return author;
    }
    public void setAuthor(String author){
        this.author = author;
    }
    public String getISBN(){
        return isbn;
    }
    public void setISBN(String isbn){
        this.isbn = isbn;
    }
}
