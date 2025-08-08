package com.examly.springapp.repository;

import com.examly.springapp.entity.Book;
import com.examly.springapp.entity.BorrowRecord;
import com.examly.springapp.entity.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {
    List<BorrowRecord> findByBorrower(Borrower borrower);
    boolean existsByBookAndReturnDateIsNull(Book book);
    Optional<BorrowRecord> findFirstByBookAndReturnDateIsNullOrderByBorrowDateDesc(Book book);
}
