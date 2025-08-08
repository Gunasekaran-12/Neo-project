package com.examly.springapp.service;

import com.examly.springapp.entity.Borrower;
import com.examly.springapp.exception.ResourceNotFoundException;
import com.examly.springapp.repository.BorrowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Service
public class BorrowerService {

    private static final Logger logger = LoggerFactory.getLogger(BorrowerService.class);

    @Autowired
    private BorrowerRepository borrowerRepository;

    // Save borrower to the database
    public Borrower saveBorrower(Borrower borrower) {
        if (borrower == null) {
            throw new IllegalArgumentException("Borrower cannot be null");
        }
        logger.info("Saving borrower with ID: {}", borrower.getId());
        return borrowerRepository.save(borrower);
    }

    // Get borrower by ID
    public Borrower getBorrowerById(Long id) {
        logger.info("Fetching borrower with ID: {}", id);
        return borrowerRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Borrower not found with ID: " + id));
    }
}
