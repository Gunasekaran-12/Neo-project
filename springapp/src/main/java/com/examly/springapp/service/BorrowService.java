package com.examly.springapp.service;

import com.examly.springapp.entity.*;
import com.examly.springapp.exception.*;
import com.examly.springapp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Optional;

@Service
public class BorrowService {

    private static final Logger logger = LoggerFactory.getLogger(BorrowService.class);

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BorrowerRepository borrowerRepository;

    @Autowired
    private BorrowRecordRepository borrowRecordRepository;

    // Borrow Book Method
    public BorrowRecord borrowBook(Long bookId, Long borrowerId) {
        logger.info("Attempting to borrow book with ID: {} by borrower with ID: {}", bookId, borrowerId);
        
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        if (!book.getAvailable()) {
            throw new BusinessValidationException("Book is not available for borrowing");
        }

       // Check if the book is already borrowed
        Optional<BorrowRecord> activeRecord = borrowRecordRepository.findFirstByBookAndReturnDateIsNullOrderByBorrowDateDesc(book);
        if (activeRecord.isPresent()) {
            throw new BusinessValidationException("Book is already borrowed");
        }

        Borrower borrower = borrowerRepository.findById(borrowerId)
                .orElseThrow(() -> new ResourceNotFoundException("Borrower not found"));

        book.setAvailable(false);
        bookRepository.save(book);

        BorrowRecord record = new BorrowRecord(book, borrower, LocalDate.now());
        record.setDueDate(LocalDate.now().plusDays(14));
        record.setReturned(false);

        logger.info("Book with ID: {} has been borrowed by borrower with ID: {}", bookId, borrowerId);

        return borrowRecordRepository.save(record);
    }

    // Return Book Method
    public BorrowRecord returnBook(Long recordId) {
        logger.info("Attempting to return borrow record with ID: {}", recordId);

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

        logger.info("Book with ID: {} has been returned and marked as available", record.getBook().getId());

        return borrowRecordRepository.save(record);
    }

    // Get Borrow Record Method
    public BorrowRecord getBorrowRecord(Long id) {
        return borrowRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Borrow record not found"));
    }
}