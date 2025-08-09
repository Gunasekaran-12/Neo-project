package com.examly.springapp.controller;

import com.examly.springapp.entity.BorrowRecord;
import com.examly.springapp.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/borrow")
public class BorrowController {

    private static final Logger logger = LoggerFactory.getLogger(BorrowController.class);

    @Autowired
    private BorrowService borrowService;

    // Borrow a book
    @PostMapping("/borrowBook")
    public ResponseEntity<BorrowRecord> borrowBook(@RequestParam Long bookId,
                                                   @RequestParam Long borrowerId) {
        logger.info("Borrowing book with ID: {} by borrower with ID: {}", bookId, borrowerId);
        BorrowRecord record = borrowService.borrowBook(bookId, borrowerId);
        return ResponseEntity.ok(record); // 200 OK for success
    }

    // Return a book
    @PostMapping("/return/{recordId}")
    public ResponseEntity<BorrowRecord> returnBook(@PathVariable Long recordId) {
        logger.info("Returning book with record ID: {}", recordId);
        BorrowRecord returned = borrowService.returnBook(recordId);
        return ResponseEntity.ok(returned); // 200 OK for success
    }

    // Get borrow record
    @GetMapping("/{id}")
    public ResponseEntity<BorrowRecord> getBorrowRecord(@PathVariable Long id) {
        logger.info("Fetching borrow record with ID: {}", id);
        BorrowRecord record = borrowService.getBorrowRecord(id);
        return ResponseEntity.ok(record);
    }
}
