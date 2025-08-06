package com.examly.springapp.service;

import com.examly.springapp.entity.Book;
import com.examly.springapp.entity.BorrowRecord;
import com.examly.springapp.entity.Borrower;
import com.examly.springapp.repository.BorrowRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BorrowRecordService {

    @Autowired
    private BorrowRecordRepository borrowRecordRepository;

    public List<BorrowRecord> findByBorrower(Borrower borrower) {
        return borrowRecordRepository.findByBorrower(borrower);
    }

    public boolean existsByBookAndReturnDateIsNull(Book book) {
        return borrowRecordRepository.existsByBookAndReturnDateIsNull(book);
    }

    public Optional<BorrowRecord> findFirstByBookAndReturnDateIsNullOrderByBorrowDateDesc(Book book) {
        return borrowRecordRepository.findFirstByBookAndReturnDateIsNullOrderByBorrowDateDesc(book);
    }

    public BorrowRecord save(BorrowRecord borrowRecord) {
        return borrowRecordRepository.save(borrowRecord);
    }

    public Optional<BorrowRecord> findById(Long id) {
        return borrowRecordRepository.findById(id);
    }

    public List<BorrowRecord> findByReturnDateIsNull() {
        return borrowRecordRepository.findByReturnDateIsNull();
    }

    public List<BorrowRecord> findByBook(Book book) {
        return borrowRecordRepository.findByBook(book);
    }

    public List<BorrowRecord> findByReturned(boolean returned) {
        return borrowRecordRepository.findByReturned(returned);
    }

    public void markRecordAsReturned(BorrowRecord activeRecord) {
        activeRecord.setReturnDate(LocalDate.now());
        activeRecord.setReturned(true);
        borrowRecordRepository.save(activeRecord);
    }

    public Object borrowBook(Long bookId, Long userId) {
        throw new UnsupportedOperationException("Unimplemented method 'borrowBook'");
    }

    public Object returnBook(Long bookId) {
        throw new UnsupportedOperationException("Unimplemented method 'returnBook'");
    }

    public Optional<BorrowRecord> getBorrowRecordById(Long id) {

        throw new UnsupportedOperationException("Unimplemented method 'getBorrowRecordById'");
    }

    public BorrowRecord saveBorrowRecord(BorrowRecord borrowRecord) {

        throw new UnsupportedOperationException("Unimplemented method 'saveBorrowRecord'");
    }
}