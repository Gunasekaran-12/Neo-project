package com.examly.springapp.controller;

import com.examly.springapp.entity.BorrowRecord;
import com.examly.springapp.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/borrow-records")
public class BorrowerController {

    @Autowired
    private BorrowService borrowRecordService;

    @PostMapping
    public ResponseEntity<BorrowRecord> createBorrowRecord(@RequestBody BorrowRecord borrowRecord) {
        BorrowRecord savedBorrowRecord = borrowRecordService.saveBorrowRecord(borrowRecord);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBorrowRecord);
    }

    @PostMapping("/borrow")
    public ResponseEntity<BorrowRecord> borrowBook(
            @RequestParam Long bookId,
            @RequestParam Long borrowerId) {
        BorrowRecord newBorrowRecord = (BorrowRecord) borrowRecordService.borrowBook(bookId, borrowerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(newBorrowRecord);
    }

    @PostMapping("/return/{bookId}")
    public ResponseEntity<BorrowRecord> returnBook(@PathVariable Long bookId) {
        BorrowRecord returnedRecord = (BorrowRecord) borrowRecordService.returnBook(bookId);
        return ResponseEntity.ok(returnedRecord);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BorrowRecord> getBorrowRecord(@PathVariable Long id) {
        Optional<BorrowRecord> borrowRecord = borrowRecordService.getBorrowRecordById(id);
        return borrowRecord.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/borrower/{borrowerId}")
    public ResponseEntity<Optional<BorrowRecord>> getBorrowRecordsByBorrower(
            @PathVariable Long borrowerId) {
        Optional<BorrowRecord> borrowRecords = borrowRecordService.getBorrowRecordById(borrowerId);
        return ResponseEntity.ok(borrowRecords);
    }
}