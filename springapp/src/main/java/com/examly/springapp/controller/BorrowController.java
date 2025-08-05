package com.examly.springapp.controller;

import com.examly.springapp.service.BorrowRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/borrow")
public class BorrowController {
    @Autowired
    private BorrowRecordService borrowService;

    @PostMapping("/{bookId}/user/{userId}")
    public ResponseEntity<?> borrowBook(
            @PathVariable Long bookId,
            @PathVariable Long userId) {
        try {
            return ResponseEntity.ok(borrowService.borrowBook(bookId, userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/return/{bookId}")
    public ResponseEntity<?> returnBook(@PathVariable Long bookId) {
        try {
            return ResponseEntity.ok(borrowService.returnBook(bookId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}