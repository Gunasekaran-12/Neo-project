package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import com.examly.springapp.entity.Book;
import com.examly.springapp.entity.BorrowRecord;
import com.examly.springapp.entity.Borrower;

public class BorrowRecordRepository {

    public List<BorrowRecord> findByBorrower(Borrower borrower) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByBorrower'");
    }

    public boolean existsByBookAndReturnDateIsNull(Book book) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'existsByBookAndReturnDateIsNull'");
    }

    public Optional<Book> findFirstByBookAndReturnDateIsNullOrderByBorrowDateDesc(Book book) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findFirstByBookAndReturnDateIsNullOrderByBorrowDateDesc'");
    }

    public BorrowRecord save(Book activeRecord) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    public Optional<BorrowRecord> findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

}
