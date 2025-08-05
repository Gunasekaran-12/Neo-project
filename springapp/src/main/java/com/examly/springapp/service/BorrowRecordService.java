package com.examly.springapp.service;

import com.examly.springapp.entity.BorrowRecord;
import com.examly.springapp.exception.*;
import com.examly.springapp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class BorrowRecordService {

    @Autowired
    private BorrowRecordRepository borrowRepo;
    
    @Autowired  // Added missing annotation
    private BookRepository bookRepo;

    public BorrowRecord borrowBook(Long bookId, Long userId) {
        if (!bookRepo.existsById(bookId)) {
            throw new ResourceNotFoundException("Book not found");
        }
        if (borrowRepo.existsByBookIdAndReturnDateIsNull(bookId)) {
            throw new BusinessValidationException("Book already borrowed");
        }
        
        BorrowRecord record = new BorrowRecord();
        record.setBookId(bookId);  // Changed from setBook to setBookId
        record.setUserId(userId);  // Changed from setId to setUserId
        record.setBorrowDate(LocalDate.now());
        return borrowRepo.save(record);
    }

    public BorrowRecord returnBook(Long bookId) {
        BorrowRecord record = borrowRepo
            .findFirstByBookIdAndReturnDateIsNullOrderByBorrowDateDesc(bookId)
            .orElseThrow(() -> new BusinessValidationException("No active borrow record"));
        
        record.setReturnDate(LocalDate.now());
        return borrowRepo.save(record);
    }

    public BorrowRecord saveBorrowRecord(BorrowRecord borrowRecord) {
        return borrowRepo.save(borrowRecord);  // Implemented instead of throwing exception
    }

    public Optional<BorrowRecord> getBorrowRecordById(Long id) {  // Changed return type
        return borrowRepo.findById(id);  // Proper implementation
    }
}