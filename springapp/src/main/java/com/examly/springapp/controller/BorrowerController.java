package com.examly.springapp.controller;

import com.examly.springapp.entity.Borrower;
import com.examly.springapp.service.BorrowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/borrowers")
@Validated
public class BorrowerController {

    @Autowired
    private BorrowerService borrowerService;

    @PostMapping
    public ResponseEntity<Borrower> addBorrower(@Valid @RequestBody Borrower borrower) {
        Borrower savedBorrower = borrowerService.saveBorrower(borrower);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBorrower);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBorrower(@PathVariable Long id) {
        return borrowerService.getBorrowerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}