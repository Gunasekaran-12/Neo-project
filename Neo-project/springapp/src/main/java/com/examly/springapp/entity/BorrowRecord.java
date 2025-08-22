package com.examly.springapp.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class BorrowRecord {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;
    
    @ManyToOne
    @JoinColumn(name = "borrower_id", nullable = false)
    private Borrower borrower;
    
    private LocalDate borrowDate = LocalDate.now();
    private LocalDate dueDate;
    private LocalDate returnDate;
    private String status = "ACTIVE"; // ACTIVE, RETURNED, OVERDUE, LOST
    
    // Constructors
    public BorrowRecord() {}

    public BorrowRecord(Book book, Borrower borrower, LocalDate dueDate) {
        this.book = book;
        this.borrower = borrower;
        this.dueDate = dueDate;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Borrower getBorrower() {
        return borrower;
    }

    public void setBorrower(Borrower borrower) {
        this.borrower = borrower;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
        if (returnDate != null) {
            this.status = "RETURNED";
        }
    }

    public String getStatus() {
        // Update status if book is overdue and not yet returned
        if ("ACTIVE".equals(status) && LocalDate.now().isAfter(dueDate) && returnDate == null) {
            status = "OVERDUE";
        }
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Business logic methods
    public boolean isActive() {
        return "ACTIVE".equals(status);
    }

    public boolean isOverdue() {
        return LocalDate.now().isAfter(dueDate) && returnDate == null;
    }
}