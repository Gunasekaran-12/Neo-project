package com.examly.springapp.controller;

import com.examly.springapp.entity.BorrowRecord;
import com.examly.springapp.exception.BusinessValidationException;
import com.examly.springapp.exception.ResourceNotFoundException;
import com.examly.springapp.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/borrow")
public class BorrowController {

    private static final Logger logger = LoggerFactory.getLogger(BorrowController.class);

    @Autowired
    private BorrowService borrowService;

    // Borrow a book
    @PostMapping("/borrowBook")
    public ResponseEntity<?> borrowBook(@RequestParam Long bookId,
                                        @RequestParam Long borrowerId) {
        logger.info("Borrowing book with ID: {} by borrower with ID: {}", bookId, borrowerId);
        try {
            BorrowRecord record = borrowService.borrowBook(bookId, borrowerId);
            return ResponseEntity.status(HttpStatus.CREATED).body(record);
        } catch (ResourceNotFoundException e) {
            return errorResponse(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (BusinessValidationException e) {
            return errorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
// Return a book
    @PostMapping("/return/{recordId}")
    public ResponseEntity<?> returnBook(@PathVariable Long recordId) {
        logger.info("Returning book with record ID: {}", recordId);
        try {
            BorrowRecord returned = borrowService.returnBook(recordId);
            return ResponseEntity.ok(returned);
        } catch (ResourceNotFoundException e) {
            return errorResponse(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (BusinessValidationException e) {
            return errorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    // Get borrow record
    @GetMapping("/{id}")
    public ResponseEntity<?> getBorrowRecord(@PathVariable Long id) {
        logger.info("Fetching borrow record with ID: {}", id);
        try {
            BorrowRecord record = borrowService.getBorrowRecord(id);
            return ResponseEntity.ok(record);
        } catch (ResourceNotFoundException e) {
            return errorResponse(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    // Helper method to create error responses
    private ResponseEntity<Map<String, String>> errorResponse(HttpStatus status, String message) {
        Map<String, String> response = new HashMap<>();
        response.put("message", message);
        return ResponseEntity.status(status).body(response);
    }
}