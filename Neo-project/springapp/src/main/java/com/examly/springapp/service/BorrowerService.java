package com.examly.springapp.service;

import com.examly.springapp.entity.Borrower;
import com.examly.springapp.exception.ResourceNotFoundException;
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
                .orElseThrow(() -> new ResourceNotFoundException("Borrower not found with id: " + id));
    }

    public Borrower addBorrower(Borrower borrower) {
        return borrowerRepository.save(borrower);
    }

    public Borrower updateBorrower(Long id, Borrower borrowerDetails) {
        Borrower borrower = getBorrowerById(id);
        borrower.setName(borrowerDetails.getName());
        borrower.setEmail(borrowerDetails.getEmail());
        borrower.setPhone(borrowerDetails.getPhone());
        return borrowerRepository.save(borrower);
    }

    public void deleteBorrower(Long id) {
        Borrower borrower = getBorrowerById(id);
        borrowerRepository.delete(borrower);
    }
}