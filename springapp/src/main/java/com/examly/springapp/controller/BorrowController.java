package com.examly.springapp.controller;

import com.examly.springapp.entity.BorrowRecord;
import com.examly.springapp.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/borrows")
public class BorrowController {

    @Autowired
    private BorrowService borrowService;

    @PostMapping("/borrow")
    public BorrowRecord borrowBook(@RequestParam Long bookId, @RequestParam Long borrowerId) {
        return borrowService.borrowBook(bookId, borrowerId);
    }

    @PostMapping("/return/{borrowId}")
    public BorrowRecord returnBook(@PathVariable Long borrowId) {
        return borrowService.returnBook(borrowId);
    }

    @GetMapping("/borrower/{borrowerId}")
    public List<BorrowRecord> getBorrowByBorrower(@PathVariable Long borrowerId) {
        return borrowService.getBorrowByBorrower(borrowerId);
    }

    @GetMapping("/book/{bookId}")
    public List<BorrowRecord> getBorrowByBook(@PathVariable Long bookId) {
        return borrowService.getBorrowByBook(bookId);
    }
}
