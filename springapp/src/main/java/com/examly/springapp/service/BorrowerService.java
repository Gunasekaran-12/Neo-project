package com.examly.springapp.service;

import com.examly.springapp.entity.Borrower;
import com.examly.springapp.repository.BorrowerRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BorrowerService {

    @Autowired
    private BorrowerRepository borrowerRepository;

    public Borrower saveBorrower(Borrower borrower) {
        return borrowerRepository.save(borrower);
    }

    public Optional<Borrower> getBorrowerById(Long id) {
        return borrowerRepository.findById(id);
    }
}