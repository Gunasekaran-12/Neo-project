package com.examly.springapp.service;

import com.examly.springapp.entity.*;
import com.examly.springapp.exception.*;
import com.examly.springapp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class BorrowService {
    
    // Other fields and annotations...

    // 1) borrowBook method - implemented fully here
    public BorrowRecord borrowBook(Long bookId, Long borrowerId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        if (!book.isAvailable()) {
            throw new BusinessValidationException("Book is not available for borrowing");
        }

        Borrower borrower = borrowerRepository.findById(borrowerId)
                .orElseThrow(() -> new ResourceNotFoundException("Borrower not found"));

        book.setAvailable(false);
        bookRepository.save(book);

        BorrowRecord record = new BorrowRecord(book, borrower, LocalDate.now());
        record.setDueDate(LocalDate.now().plusDays(14));
        record.setReturned(false);

        return borrowRecordRepository.save(record);
    }

    // 2) returnBook method - implemented fully here
    public BorrowRecord returnBook(Long recordId) {
        BorrowRecord record = borrowRecordRepository.findById(recordId)
                .orElseThrow(() -> new ResourceNotFoundException("Borrow record not found"));

        if (record.isReturned()) {
            throw new BusinessValidationException("Book already returned");
        }

        record.setReturnDate(LocalDate.now());
        record.setReturned(true);

        Book book = record.getBook();
        book.setAvailable(true);
        bookRepository.save(book);

        return borrowRecordRepository.save(record);
    }

    // 3) getBorrowRecord method - implemented fully here
    public BorrowRecord getBorrowRecord(Long id) {
        return borrowRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Borrow record not found"));
    }
}
