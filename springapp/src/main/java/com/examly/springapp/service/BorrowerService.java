package com.examly.springapp.service;

import com.examly.springapp.entity.Borrower;
import com.examly.springapp.repository.BorrowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BorrowerService {

    @Autowired
    private BorrowerRepository borrowerRepository;

    // Save a new borrower
    public Borrower saveBorrower(Borrower borrower) {
        return borrowerRepository.save(borrower);
    }

    // Get borrower by ID, returning Optional to safely handle absence of data
    public Optional<Borrower> getBorrowerById(Long id) {
        return borrowerRepository.findById(id);  // Fetch borrower from repository
    }
}
