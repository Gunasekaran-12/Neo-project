package com.examly.springapp.service;

import com.examly.springapp.entity.*;
import com.examly.springapp.exception.*;
import com.examly.springapp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class BorrowRecordService {

    @Autowired
    private BorrowRecordRepository borrowRepo;
    
    @Autowired
    private BookRepository bookRepo;
    
    @Autowired
    private BorrowerRepository borrowerRepo;

    @Transactional
    public BorrowRecord borrowBook(Long bookId, Long borrowerId) {
        Book book = bookRepo.findById(bookId)
            .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + bookId));

        Borrower borrower = borrowerRepo.findById(borrowerId)
            .orElseThrow(() -> new ResourceNotFoundException("Borrower not found with id: " + borrowerId));

        if (borrowRepo.existsByBookAndReturnDateIsNull(book)) {
            throw new BusinessValidationException("Book is already borrowed");
        }

        BorrowRecord record = new BorrowRecord();
        record.setBook(book);
        record.setBorrower(borrower);
        record.setBorrowDate(LocalDate.now());
        record.setDueDate(LocalDate.now().plusWeeks(2)); // 2 weeks due date
        return borrowRepo.save(record);
    }
@Transactional
    public BorrowRecord returnBook(Long bookId) {
        Book book = bookRepo.findById(bookId)
            .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + bookId));

        BorrowRecord record = borrowRepo
            .findFirstByBookAndReturnDateIsNullOrderByBorrowDateDesc(book)
            .orElseThrow(() -> new BusinessValidationException("No active borrow record for this book"));
        
        record.setReturnDate(LocalDate.now());
        return borrowRepo.save(record);
    }

    @Transactional
    public BorrowRecord saveBorrowRecord(BorrowRecord borrowRecord) {
        if (borrowRecord.getBook() == null) {
            throw new BusinessValidationException("Book must be specified");
        }
        if (borrowRecord.getBorrower() == null) {
            throw new BusinessValidationException("Borrower must be specified");
        }
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
}
