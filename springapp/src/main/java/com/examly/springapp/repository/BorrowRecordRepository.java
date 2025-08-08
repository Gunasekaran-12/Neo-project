package com.examly.springapp.repository;

import com.examly.springapp.entity.Book;
import com.examly.springapp.entity.BorrowRecord;
import com.examly.springapp.entity.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {

    // Find all borrow records for a specific borrower
    List<BorrowRecord> findByBorrower(Borrower borrower);

    // Check if a book is currently borrowed (has no return date)
    boolean existsByBookAndReturnDateIsNull(Book book);

    // Find the most recent borrow record for a book (that is not returned)
    Optional<BorrowRecord> findFirstByBookAndReturnDateIsNullOrderByBorrowDateDesc(Book book);
    
    // Additional custom queries can be added here if needed:
    // Example: Get overdue books
    /*
    @Query("SELECT br FROM BorrowRecord br WHERE br.dueDate < :currentDate AND br.returnDate IS NULL")
    List<BorrowRecord> findOverdueBooks(@Param("currentDate") LocalDate currentDate);
    */
}
