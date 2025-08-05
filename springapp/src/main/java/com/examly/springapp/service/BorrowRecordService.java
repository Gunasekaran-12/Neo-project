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
    public BorrowRecord saveBorrowRecord(BorrowRecord borrowRecord) {
        if (borrowRecord.getBook() == null) {
            throw new BusinessValidationException("Book must be specified");
        }
        if (borrowRecord.getBorrower() == null) {
            throw new BusinessValidationException("Borrower must be specified");
        }
        return borrowRepo.save(borrowRecord);
    }

    @Transactional
    public BorrowRecord borrowBook(Long bookId, Long borrowerId) {
        Book book = bookRepo.findById(bookId)
            .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + bookId));

        Borrower borrower = borrowerRepo.findById(borrowerId)
            .orElseThrow(() -> new ResourceNotFoundException("Borrower not found with id: " + borrowerId));

        if (isBookCurrentlyBorrowed(book)) {
            throw new BusinessValidationException("Book is already borrowed");
        }

        BorrowRecord newRecord = new BorrowRecord();
        newRecord.setBook(book);
        newRecord.setBorrower(borrower);
        newRecord.setBorrowDate(LocalDate.now());
        newRecord.setDueDate(LocalDate.now().plusWeeks(2));
        newRecord.setReturned(false);
        
        return borrowRepo.save(newRecord);
    }

    @Transactional
    public BorrowRecord returnBook(Long bookId) {
        Book book = bookRepo.findById(bookId)
            .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + bookId));

        Book activeRecord = borrowRepo
            .findFirstByBookAndReturnDateIsNullOrderByBorrowDateDesc(book)
            .orElseThrow(() -> new BusinessValidationException("No active borrow record for this book"));
        
        activeRecord.setReturnDate(LocalDate.now());
        activeRecord.setReturnDate(true);
        
        return borrowRepo.save(activeRecord);
    }

    public Optional<BorrowRecord> getBorrowRecordById(Long id) {
        return borrowRepo.findById(id);
    }

    public List<BorrowRecord> getBorrowRecordsByBorrower(Long borrowerId) {
        Borrower borrower = borrowerRepo.findById(borrowerId)
            .orElseThrow(() -> new ResourceNotFoundException("Borrower not found"));
        return borrowRepo.findByBorrower(borrower);
    }

    private boolean isBookCurrentlyBorrowed(Book book) {
        return borrowRepo.existsByBookAndReturnDateIsNull(book);
    }
}
