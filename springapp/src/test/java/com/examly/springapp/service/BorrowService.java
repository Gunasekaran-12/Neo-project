package com.examly.springapp.service;

import com.examly.springapp.entity.Book;
import com.examly.springapp.entity.BorrowRecord;
import com.examly.springapp.entity.Borrower;
import com.examly.springapp.exception.BusinessValidationException;
import com.examly.springapp.exception.ResourceNotFoundException;
import com.examly.springapp.repository.BookRepository;
import com.examly.springapp.repository.BorrowRecordRepository;
import com.examly.springapp.repository.BorrowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class BorrowService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BorrowerRepository borrowerRepository;

    @Autowired
    private BorrowRecordRepository borrowRecordRepository;

    public BorrowRecord borrowBook(long bookId, long borrowerId) {
        // Find the book
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        // Check availability
        if (!book.getAvailable()) {
            throw new BusinessValidationException("Book is not available for borrowing");
        }
// Find the borrower
        Borrower borrower = borrowerRepository.findById(borrowerId)
                .orElseThrow(() -> new ResourceNotFoundException("Borrower not found"));

        // Mark the book as unavailable
        book.setAvailable(false);
        bookRepository.save(book);

        // Create a borrow record
        BorrowRecord record = new BorrowRecord();
        record.setBook(book);
        record.setBorrower(borrower);
        record.setBorrowDate(LocalDate.now());
        record.setReturnDate(null);

        return borrowRecordRepository.save(record);
    }

    public BorrowRecord returnBook(long borrowRecordId) {
        // Find the borrow record
        BorrowRecord record = borrowRecordRepository.findById(borrowRecordId)
                .orElseThrow(() -> new ResourceNotFoundException("Borrow record not found"));

        // Check if already returned
        if (record.getReturnDate() != null) {
            throw new BusinessValidationException("Book already returned");
        }

        // Mark as returned
        record.setReturnDate(LocalDate.now());

        // Update book availability
        Book book = record.getBook();
        book.setAvailable(true);
        bookRepository.save(book);

        return borrowRecordRepository.save(record);
    }
}