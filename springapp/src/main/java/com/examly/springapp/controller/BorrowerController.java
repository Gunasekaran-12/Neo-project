package com.examly.springapp.controller;

import com.examly.springapp.entity.Borrower;
import com.examly.springapp.service.BorrowerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/borrow-records")
public class BorrowerController {

   @Autowired
      private BorrowerService borrowerService;
      
      // Create a new borrower
      @PostMapping
      public ResponseEntity<Borrower> createBorrower(@RequestBody Borrower borrower) {
          Borrower savedBorrower = borrowerService.saveBorrower(borrower);
          return ResponseEntity.status(201).body(savedBorrower);
      }
      
      // Get borrower by ID
      @GetMapping("/{id}")
      public ResponseEntity<Borrower> getBorrower(@PathVariable Long id) {
          Optional<Borrower> borrower = borrowerService.getBorrowerById(id);
          return borrower.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
      }

}