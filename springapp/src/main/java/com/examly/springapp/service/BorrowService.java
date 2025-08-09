// package com.examly.springapp.service;

// import com.examly.springapp.entity.*;
// import com.examly.springapp.exception.*;
// import com.examly.springapp.repository.*;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;
// import java.time.LocalDate;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import java.util.Optional;

// @Service
// public class BorrowService {

//     private static final Logger logger = LoggerFactory.getLogger(BorrowService.class);

//     @Autowired
//     private BookRepository bookRepository;

//     @Autowired
//     private BorrowerRepository borrowerRepository;

//     @Autowired
//     private BorrowRecordRepository borrowRecordRepository;

//     // Borrow Book Method
//     public BorrowRecord borrowBook(Long bookId, Long borrowerId) {
//         logger.info("Attempting to borrow book with ID: {} by borrower with ID: {}", bookId, borrowerId);
        
//         Book book = bookRepository.findById(bookId)
//                 .orElseThrow(() -> new ResourceNotFoundException("Book not found"));

//         if (!book.getAvailable()) {
//             throw new BusinessValidationException("Book is not available for borrowing");
//         }

//        // Check if the book is already borrowed
//         Optional<BorrowRecord> activeRecord = borrowRecordRepository.findFirstByBookAndReturnDateIsNullOrderByBorrowDateDesc(book);
//         if (activeRecord.isPresent()) {
//             throw new BusinessValidationException("Book is already borrowed");
//         }

//         Borrower borrower = borrowerRepository.findById(borrowerId)
//                 .orElseThrow(() -> new ResourceNotFoundException("Borrower not found"));

//         book.setAvailable(false);
//         bookRepository.save(book);

//         BorrowRecord record = new BorrowRecord(book, borrower, LocalDate.now());
//         record.setDueDate(LocalDate.now().plusDays(14));
//         record.setReturned(false);

//         logger.info("Book with ID: {} has been borrowed by borrower with ID: {}", bookId, borrowerId);

//         return borrowRecordRepository.save(record);
//     }

//     // Return Book Method
//     public BorrowRecord returnBook(Long recordId) {
//         logger.info("Attempting to return borrow record with ID: {}", recordId);

//         BorrowRecord record = borrowRecordRepository.findById(recordId)
//                 .orElseThrow(() -> new ResourceNotFoundException("Borrow record not found"));

//         if (record.isReturned()) {
//             throw new BusinessValidationException("Book already returned");
//         }

//         record.setReturnDate(LocalDate.now());
//         record.setReturned(true);

//         Book book = record.getBook();
//         book.setAvailable(true);
//         bookRepository.save(book);

//         logger.info("Book with ID: {} has been returned and marked as available", record.getBook().getId());

//         return borrowRecordRepository.save(record);
//     }

//     // Get Borrow Record Method
//     public BorrowRecord getBorrowRecord(Long id) {
//         return borrowRecordRepository.findById(id)
//                 .orElseThrow(() -> new ResourceNotFoundException("Borrow record not found"));
//     }
// }

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
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

    /**
     * Borrow a book by ID for a borrower
     */
    public BorrowRecord borrowBook(Long bookId, Long borrowerId) {
        logger.info("Attempting to borrow book with ID: {} by borrower with ID: {}", bookId, borrowerId);

        // Find the book
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        // Check availability
        if (!book.getAvailable()) {
            throw new BusinessValidationException("Book is not available for borrowing");
        }

        // Ensure not already borrowed by another active record
        Optional<BorrowRecord> activeRecord =
                borrowRecordRepository.findFirstByBookAndReturnDateIsNullOrderByBorrowDateDesc(book);
        if (activeRecord.isPresent()) {
            throw new BusinessValidationException("Book is already borrowed");
        }

        // Find the borrower
        Borrower borrower = borrowerRepository.findById(borrowerId)
                .orElseThrow(() -> new ResourceNotFoundException("Borrower not found"));

        // Mark the book as unavailable
        book.setAvailable(false);
        bookRepository.save(book);

        // Create borrow record
        BorrowRecord record = new BorrowRecord();
        record.setBook(book);
        record.setBorrower(borrower);
        record.setBorrowDate(LocalDate.now());
        record.setReturnDate(null);
        record.setDueDate(LocalDate.now().plusDays(14)); // two weeks due date
        record.setReturned(false);

        logger.info("Book with ID: {} has been borrowed by borrower with ID: {}", bookId, borrowerId);

        return borrowRecordRepository.save(record);
    }

    /**
     * Return a borrowed book by record ID
     */
    public BorrowRecord returnBook(Long recordId) {
        logger.info("Attempting to return borrow record with ID: {}", recordId);

        // Find borrow record
        BorrowRecord record = borrowRecordRepository.findById(recordId)
                .orElseThrow(() -> new ResourceNotFoundException("Borrow record not found"));

        // Prevent double return
        if (record.isReturned() || record.getReturnDate() != null) {
            throw new BusinessValidationException("Book already returned");
        }

        // Mark as returned
        record.setReturnDate(LocalDate.now());
        record.setReturned(true);

        // Update book availability
        Book book = record.getBook();
        book.setAvailable(true);
        bookRepository.save(book);

        logger.info("Book with ID: {} has been returned and marked as available", book.getId());

        return borrowRecordRepository.save(record);
    }

    /**
     * Get a borrow record by ID
     */
    public BorrowRecord getBorrowRecord(Long id) {
        return borrowRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Borrow record not found"));
    }
}