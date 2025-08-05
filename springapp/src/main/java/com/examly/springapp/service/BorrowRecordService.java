package com.examly.springapp.service;

import com.examly.springapp.entity.Book;
import com.examly.springapp.entity.BorrowRecord;
import com.examly.springapp.exception.BusinessValidationException;
import com.examly.springapp.exception.ResourceNotFoundException;
import com.examly.springapp.repository.BookRepository;
import com.examly.springapp.repository.BorrowRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class BorrowRecordService {

    @Autowired
    private BorrowRecordRepository borrowRecordRepository;

    @Autowired
    private BookRepository bookRepository;

    // Existing methods
    public BorrowRecord saveBorrowRecord(BorrowRecord borrowRecord) {
        return borrowRecordRepository.save(borrowRecord);
    }

    public Optional<BorrowRecord> getBorrowRecordById(Long id) {
        return borrowRecordRepository.findById(id);
    }

    // Borrow functionality
    public BorrowRecord borrowBook(Long bookId, Long userId) {
        // Check if book exists
        Book book = bookRepository.findById(bookId)
            .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        
        // Check if book is already borrowed
        if (borrowRecordRepository.existsByBookIdAndReturnDateIsNull(bookId)) {
            throw new BusinessValidationException("Book already borrowed");
        }
        
        // Create new borrow record
        BorrowRecord record = new BorrowRecord();
        record.setBook(bookId);
        record.setId(userId);
        record.setBorrowDate(LocalDate.now());
        
        return borrowRecordRepository.save(record);
    }

    // Return functionality
    public BorrowRecord returnBook(Long bookId) {
        // Find active borrow record
        BorrowRecord record = borrowRecordRepository
            .findFirstByBookIdAndReturnDateIsNullOrderByBorrowDateDesc(bookId)
            .orElseThrow(() -> new BusinessValidationException("No active borrow record"));
        
        // Set return date
        record.setReturnDate(LocalDate.now());
        
        return borrowRecordRepository.save(record);
    }
}