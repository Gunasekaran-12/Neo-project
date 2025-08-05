package com.examly.springapp.controller;

import com.examly.springapp.entity.BorrowRecord;
import com.examly.springapp.service.BorrowRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/borrow-records")
public class BorrowRecordController {

    @Autowired
    private BorrowRecordService borrowRecordService;

    @PostMapping
    public ResponseEntity<BorrowRecord> createBorrowRecord(@RequestBody BorrowRecord borrowRecord) {
        BorrowRecord savedRecord = borrowRecordService.saveBorrowRecord(borrowRecord);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRecord);
    }

    @PostMapping("/borrow")
    public ResponseEntity<BorrowRecord> borrowBook(
            @RequestParam Long bookId,
            @RequestParam Long borrowerId) {
        BorrowRecord record = borrowRecordService.borrowBook(bookId, borrowerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(record);
    }

    @PostMapping("/return/{bookId}")
    public ResponseEntity<BorrowRecord> returnBook(@PathVariable Long bookId) {
        BorrowRecord record = borrowRecordService.returnBook(bookId);
        return ResponseEntity.ok(record);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BorrowRecord> getBorrowRecord(@PathVariable Long id) {
        Optional<BorrowRecord> record = borrowRecordService.getBorrowRecordById(id);
        return record.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/borrower/{borrowerId}")
    public ResponseEntity<Optional<BorrowRecord>> getBorrowRecordsByBorrower(
            @PathVariable Long borrowerId) {
        Optional<BorrowRecord> records = borrowRecordService.getBorrowRecordById(borrowerId);
        return ResponseEntity.ok(records);
    }
}