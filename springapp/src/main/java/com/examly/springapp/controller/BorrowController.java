package com.examly.springapp.controller;

import com.examly.springapp.entity.BorrowRecord;
import com.examly.springapp.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/borrow")
public class BorrowController {
    @Autowired
    private BorrowService borrowService;

   @PostMapping("/borrow/{bookId}/{borrowerId}")
    public ResponseEntity<Object> borrowBook(@PathVariable Long bookId, @PathVariable Long borrowerId) {
        return ResponseEntity.ok(borrowService.borrowBook(bookId, borrowerId));
    }
    
    @PostMapping("/return/{recordId}")
    public ResponseEntity<Object> returnBook(@PathVariable Long recordId) {
        return ResponseEntity.ok(borrowService.returnBook(recordId));
    }
}