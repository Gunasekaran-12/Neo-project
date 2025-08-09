package com.examly.springapp.controller;

import com.examly.springapp.entity.Borrower;
import com.examly.springapp.service.BorrowerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/borrowers")
public class BorrowerController {

    private static final Logger logger = LoggerFactory.getLogger(BorrowerController.class);

    @Autowired
    private BorrowerService borrowerService;

    // Add a new borrower
    @PostMapping
    public ResponseEntity<Borrower> addBorrower(@Valid @RequestBody Borrower borrower) {
        logger.info("Attempting to add borrower with name: {}", borrower.getName());

        Borrower savedBorrower = borrowerService.saveBorrower(borrower);

        logger.info("Borrower added with ID: {}", savedBorrower.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBorrower);
    }

    // Get borrower by ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getBorrower(@PathVariable Long id) {
        logger.info("Attempting to fetch borrower with ID: {}", id);

        return borrowerService.getBorrowerById(id)
            .<ResponseEntity<Object>>map(borrower -> {
                logger.info("Borrower found with ID: {}", id);
                return ResponseEntity.ok(borrower); // returning borrower as Object
            })
            .orElseGet(() -> {
                logger.warn("Borrower with ID {} not found", id);
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Borrower not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response); // returning error map
            });
    }
}
