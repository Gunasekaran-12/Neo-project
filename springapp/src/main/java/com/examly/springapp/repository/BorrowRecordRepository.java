package com.examly.springapp.repository;

import com.examly.springapp.entity.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {
    boolean existsByBookIdAndReturnDateIsNull(Long bookId);
    Optional<BorrowRecord> findFirstByBookIdAndReturnDateIsNullOrderByBorrowDateDesc(Long bookId);
}