package com.examly.springapp.controller;

import com.examly.springapp.entity.BorrowRecord;
import com.examly.springapp.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/borrow")
public class BorrowController {

    @Autowired
    private BorrowService borrowService;

    @PostMapping
    public ResponseEntity<BorrowRecord> borrowBook(
            @RequestParam Long bookId,
            @RequestParam Long borrowerId,
            @RequestParam String dueDate) {
        return ResponseEntity.ok(borrowService.borrowBook(
            bookId, 
            borrowerId));
    }

    @PostMapping("/{id}/return")
    public ResponseEntity<BorrowRecord> returnBook(@PathVariable Long id) {
        return ResponseEntity.ok(borrowService.returnBook(id));
    }

    @GetMapping("/active")
    public List<BorrowRecord> getActiveBorrows() {
        return borrowService.getActiveBorrowRecords();
    }

    @GetMapping("/borrower/{borrowerId}")
    public List<BorrowRecord> getBorrowsByBorrower(@PathVariable Long borrowerId) {
        return borrowService.getBorrowRecordsByBorrower(borrowerId);
    }
}