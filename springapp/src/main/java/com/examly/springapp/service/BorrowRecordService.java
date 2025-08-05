package com.examly.springapp.service;

import com.examly.springapp.entity.Book;
import com.examly.springapp.entity.BorrowRecord;
import com.examly.springapp.entity.Borrower;
import com.examly.springapp.exception.*;
import com.examly.springapp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BorrowRecordService {

    private final BorrowRecordRepository borrowRepo;
    private final BookRepository bookRepo;
    private final BorrowerRepository borrowerRepo;

    @Autowired
    public BorrowRecordService(BorrowRecordRepository borrowRepo, 
                             BookRepository bookRepo,
                             BorrowerRepository borrowerRepo) {
        this.borrowRepo = borrowRepo;
        this.bookRepo = bookRepo;
        this.borrowerRepo = borrowerRepo;
    }

    @Transactional
    public BorrowRecord borrowBook(Long bookId, Long borrowerId) {
        // Validate and get the book
        Book book = bookRepo.findById(bookId)
            .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + bookId));

        // Validate and get the borrower
        Borrower borrower = borrowerRepo.findById(borrowerId)
            .orElseThrow(() -> new ResourceNotFoundException("Borrower not found with id: " + borrowerId));

        // Check if book is already borrowed
        if (isBookCurrentlyBorrowed(book)) {
            throw new BusinessValidationException("Book is already borrowed");
        }

        // Create and save new borrow record
        BorrowRecord borrowRecord = createBorrowRecord(book, borrower);
        return borrowRepo.save(borrowRecord);
    }

    @Transactional
    public BorrowRecord returnBook(Long bookId) {
        // Validate book exists
        Book book = bookRepo.findById(bookId)
            .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + bookId));

        // Find and return active borrow record
        Book activeRecord = findActiveBorrowRecord(book);
        return processBookReturn(activeRecord);
    }
    
    @Transactional
    public BorrowRecord saveBorrowRecord(BorrowRecord borrowRecord) {
        validateBorrowRecord(borrowRecord);
        return borrowRepo.save(borrowRecord);
    }

    public Optional<BorrowRecord> getBorrowRecordById(Long id) {
        return borrowRepo.findById(id);
    }

    public List<BorrowRecord> getBorrowRecordsByBorrower(Long borrowerId) {
        Borrower borrower = borrowerRepo.findById(borrowerId)
            .orElseThrow(() -> new ResourceNotFoundException("Borrower not found"));
        return borrowRepo.findByBorrower(borrower);
    }

    // Helper methods
    private boolean isBookCurrentlyBorrowed(Book book) {
        return borrowRepo.existsByBookAndReturnDateIsNull(book);
    }

    private BorrowRecord createBorrowRecord(Book book, Borrower borrower) {
        BorrowRecord record = new BorrowRecord();
        record.setBook(book);
        record.setBorrower(borrower);
        record.setBorrowDate(LocalDate.now());
        record.setDueDate(LocalDate.now().plusWeeks(2));
        record.setReturned(false);
        return record;
    }

    private Book findActiveBorrowRecord(Book book) {
        return borrowRepo.findFirstByBookAndReturnDateIsNullOrderByBorrowDateDesc(book)
            .orElseThrow(() -> new BusinessValidationException("No active borrow record for this book"));
    }

    private BorrowRecord processBookReturn(Book activeRecord) {
            activeRecord.setReturnDate(LocalDate.now());
            activeRecord.setReturnDate(true);
            return borrowRepo.save(activeRecord);
    }

    private void validateBorrowRecord(BorrowRecord borrowRecord) {
        if (borrowRecord.getBook() == null) {
            throw new BusinessValidationException("Book must be specified");
        }
        if (borrowRecord.getBorrower() == null) {
            throw new BusinessValidationException("Borrower must be specified");
        }
    }
}