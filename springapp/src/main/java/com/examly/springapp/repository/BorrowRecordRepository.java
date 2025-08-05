package com.examly.springapp.repository;

import com.examly.springapp.entity.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {
    List<BorrowRecord> findByBorrowerId(Long borrowerId);
    List<BorrowRecord> findByBookId(Long bookId);
}
