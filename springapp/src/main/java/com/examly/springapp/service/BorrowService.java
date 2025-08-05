package com.examly.springapp.service;

import com.examly.springapp.entity.*;
import com.examly.springapp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BorrowService {

    @Autowired
    private BorrowRecordRepository borrowRecordRepo;

    @Autowired
    private BookRepository bookRepo;

    @Autowired
    private BorrowerRepository borrowerRepo;

    public BorrowRecord borrowBook(Long bookId, Long borrowerId) {
        Book book = bookRepo.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (!book.isAvailable()) {
            throw new RuntimeException("Book is already borrowed");
        }

        Borrower borrower = borrowerRepo.findById(borrowerId)
                .orElseThrow(() -> new RuntimeException("Borrower not found"));

        book.setAvailable(false);
        bookRepo.save(book);

        BorrowRecord record = new BorrowRecord();
        record.setBook(book);
        record.setBorrower(borrower);
        record.setBorrowDate(LocalDateTime.now());

        return borrowRecordRepo.save(record);
    }

    public BorrowRecord returnBook(Long borrowId) {
        BorrowRecord record = borrowRecordRepo.findById(borrowId)
                .orElseThrow(() -> new RuntimeException("Borrow record not found"));

        if (record.getReturnDate() != null) {
            throw new RuntimeException("Book already returned");
        }

        record.setReturnDate(LocalDateTime.now());

        Book book = record.getBook();
        book.setAvailable(true);
        bookRepo.save(book);

        return borrowRecordRepo.save(record);
    }

    public List<BorrowRecord> getBorrowByBorrower(Long borrowerId) {
        return borrowRecordRepo.findByBorrowerId(borrowerId);
    }

    public List<BorrowRecord> getBorrowByBook(Long bookId) {
        return borrowRecordRepo.findByBookId(bookId);
    }
}
