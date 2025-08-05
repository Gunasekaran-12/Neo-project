package com.examly.springapp.controller;
import com.examly.springapp.entity.BorrowRecord;
import com.examly.springapp.service.BorrowRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/borrow-records")
public class BorrowRecordController {

    @Autowired
    private BorrowRecordService borrowRecordService;

    @PostMapping
    public ResponseEntity<BorrowRecord> createBorrowRecord(@RequestBody BorrowRecord borrowRecord) {
        BorrowRecord savedBorrowRecord = borrowRecordService.saveBorrowRecord(borrowRecord);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBorrowRecord);
    }

    @PostMapping("/borrow")
    public ResponseEntity<BorrowRecord> borrowBook(
            @RequestParam Long bookId,
            @RequestParam Long borrowerId) {
        BorrowRecord newBorrowRecord = borrowRecordService.borrowBook(bookId, borrowerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(newBorrowRecord);
    }

    @PostMapping("/return/{bookId}")
    public ResponseEntity<BorrowRecord> returnBook(@PathVariable Long bookId) {
        BorrowRecord returnedRecord = borrowRecordService.returnBook(bookId);
        return ResponseEntity.ok(returnedRecord);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BorrowRecord> getBorrowRecord(@PathVariable Long id) {
        Optional<BorrowRecord> borrowRecord = borrowRecordService.getBorrowRecordById(id);
        return borrowRecord.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/borrower/{borrowerId}")
    public ResponseEntity<List<BorrowRecord>> getBorrowRecordsByBorrower(
            @PathVariable Long borrowerId) {
        List<BorrowRecord> borrowRecords = borrowRecordService.getBorrowRecordsByBorrower(borrowerId);
        return ResponseEntity.ok(borrowRecords);
    }
}