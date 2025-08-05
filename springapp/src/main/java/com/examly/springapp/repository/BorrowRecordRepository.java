package com.examly.springapp.repository;

import com.examly.springapp.entity.BorrowRecord;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {

    Optional<BorrowRecord> findFirstByBookIdAndReturnDateIsNullOrderByBorrowDateDesc(Long bookId);
}