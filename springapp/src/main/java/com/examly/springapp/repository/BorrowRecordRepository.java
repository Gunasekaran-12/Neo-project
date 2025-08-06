package com.examly.springapp.repository;

import com.examly.springapp.entity.Book;
import com.examly.springapp.entity.BorrowRecord;
import com.examly.springapp.entity.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {
    
    // Find all borrow records for a specific borrower
    List<BorrowRecord> findByBorrower(Borrower borrower);
    
    // Check if a book is currently borrowed (has no return date)
    boolean existsByBookAndReturnDateIsNull(Book book);
    
    // Find the most recent active borrow record for a book
    Optional<BorrowRecord> findFirstByBookAndReturnDateIsNullOrderByBorrowDateDesc(Book book);
    
    // Find all active borrow records (not returned)
    List<BorrowRecord> findByReturnDateIsNull();
    
    // Find all borrow records for a specific book
    List<BorrowRecord> findByBook(Book book);
    
    // Find borrow records by return status
    List<BorrowRecord> findByReturned(boolean returned);
}