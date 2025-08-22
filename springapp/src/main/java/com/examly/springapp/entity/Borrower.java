package com.examly.springapp.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Borrower {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private String phone;
    
    private LocalDate membershipDate = LocalDate.now();
    
    @OneToMany(mappedBy = "borrower", cascade = CascadeType.ALL)
    private List<BorrowRecord> borrowRecords = new ArrayList<>();
    
    // Constructors
    public Borrower() {
    }
    
    public Borrower(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public LocalDate getMembershipDate() {
        return membershipDate;
    }

    public void setMembershipDate(LocalDate membershipDate) {
        this.membershipDate = membershipDate;
    }

    public List<BorrowRecord> getBorrowRecords() {
        return borrowRecords;
    }

    public void setBorrowRecords(List<BorrowRecord> borrowRecords) {
        this.borrowRecords = borrowRecords;
    }

    // Helper method to add borrow record
    public void addBorrowRecord(BorrowRecord borrowRecord) {
        borrowRecords.add(borrowRecord);
        borrowRecord.setBorrower(this);
    }
}