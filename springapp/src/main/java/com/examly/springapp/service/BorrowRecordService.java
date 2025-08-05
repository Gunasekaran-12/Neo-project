package com.examly.springapp.service;

import com.examly.springapp.entity.BorrowRecord;
import com.examly.springapp.repository.BorrowRecordRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BorrowRecordService {

    @Autowired
    private BorrowRecordRepository borrowRecordRepository;

    public BorrowRecord saveBorrowRecord(BorrowRecord borrowRecord) {
        return borrowRecordRepository.save(borrowRecord);
    }

    public Optional<BorrowRecord> getBorrowRecordById(Long id) {
        return borrowRecordRepository.findById(id);
    }
}