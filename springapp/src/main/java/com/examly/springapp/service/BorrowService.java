package com.examly.springapp.service;

import com.examly.springapp.entity.*;
import com.examly.springapp.exception.*;
import com.examly.springapp.repository.*;
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

    // borrowBook method
    public BorrowRecord borrowBook(Long bookId, Long borrowerId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        if (!book.getAvailable()) {
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

    // returnBook method
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

    // getBorrowRecord method
    public BorrowRecord getBorrowRecord(Long id) {
        return borrowRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Borrow record not found"));
    }
}
