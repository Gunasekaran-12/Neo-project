package com.examly.springapp.controller;

import com.examly.springapp.entity.Borrower;
import com.examly.springapp.service.BorrowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/borrowers")
public class BorrowerController {

    private final BorrowerService borrowerService;

    @Autowired
    public BorrowerController(BorrowerService borrowerService) {
        this.borrowerService = borrowerService;
    }

    @GetMapping
    public List<Borrower> getAllBorrowers() {
        return borrowerService.getAllBorrowers();
    }

    @GetMapping("/{id}")
    public Borrower getBorrowerById(@PathVariable Long id) {
        return borrowerService.getBorrowerById(id);
    }

    @PostMapping
    public Borrower addBorrower(@RequestBody Borrower borrower) {
        return borrowerService.addBorrower(borrower);
    }

    @PutMapping("/{id}")
    public Borrower updateBorrower(@PathVariable Long id, @RequestBody Borrower borrowerDetails) {
        return borrowerService.updateBorrower(id, borrowerDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteBorrower(@PathVariable Long id) {
        borrowerService.deleteBorrower(id);
    }
}