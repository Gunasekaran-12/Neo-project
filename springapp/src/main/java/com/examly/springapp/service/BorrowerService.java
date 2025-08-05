package com.examly.springapp.service;

import com.examly.springapp.entity.Borrower;
import com.examly.springapp.repository.BorrowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowerService {

    @Autowired
    private BorrowerRepository borrowerRepository;

    public List<Borrower> getAllBorrowers() {
        return borrowerRepository.findAll();
    }

    public Borrower getBorrowerById(Long id) {
        return borrowerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Borrower not found"));
    }

    public Borrower addBorrower(Borrower borrower) {
        if (borrowerRepository.findByEmail(borrower.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        return borrowerRepository.save(borrower);
    }

    public Borrower updateBorrower(Long id, Borrower borrowerDetails) {
        Borrower borrower = getBorrowerById(id);

        if (!borrower.getEmail().equals(borrowerDetails.getEmail()) &&
            borrowerRepository.findByEmail(borrowerDetails.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        borrower.setName(borrowerDetails.getName());
        borrower.setEmail(borrowerDetails.getEmail());
        borrower.setPhoneNumber(borrowerDetails.getPhoneNumber());

        return borrowerRepository.save(borrower);
    }

    public void deleteBorrower(Long id) {
        borrowerRepository.deleteById(id);
    }
}
