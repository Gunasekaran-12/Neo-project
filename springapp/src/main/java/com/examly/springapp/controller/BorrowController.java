package com.examly.springapp.controller;

import com.examly.springapp.entity.BorrowRecord;
import com.examly.springapp.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/borrow")
public class BorrowController {

    @Autowired
    private BorrowService borrowService;

    @PostMapping("/borrowBook")
    public ResponseEntity<BorrowRecord> borrowBook(
            @RequestParam Long bookId,
            @RequestParam Long borrowerId) {
        BorrowRecord record = borrowService.borrowBook(bookId, borrowerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(record);
    }

    @PostMapping("/returnBook/{recordId}")
    public ResponseEntity<BorrowRecord> returnBook(@PathVariable Long recordId) {
        BorrowRecord returned = borrowService.returnBook(recordId);
        return ResponseEntity.ok(returned);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BorrowRecord> getBorrowRecord(@PathVariable Long id) {
        BorrowRecord record = borrowService.getBorrowRecord(id);
        return ResponseEntity.ok(record);
    }
}
