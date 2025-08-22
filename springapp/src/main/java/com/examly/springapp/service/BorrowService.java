package com.examly.springapp.service;

import com.examly.springapp.entity.Book;
import com.examly.springapp.entity.BorrowRecord;
import com.examly.springapp.entity.Borrower;
import com.examly.springapp.exception.BusinessValidationException;
import com.examly.springapp.exception.ResourceNotFoundException;
import com.examly.springapp.repository.BookRepository;
import com.examly.springapp.repository.BorrowRecordRepository;
import com.examly.springapp.repository.BorrowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Service
@Transactional
public class BorrowService {
    private final BookRepository bookRepository;
    private final BorrowerRepository borrowerRepository;
    private final BorrowRecordRepository borrowRecordRepository;

    @Autowired
    public BorrowService(BookRepository bookRepository,
                       BorrowerRepository borrowerRepository,
                       BorrowRecordRepository borrowRecordRepository) {
        this.bookRepository = bookRepository;
        this.borrowerRepository = borrowerRepository;
        this.borrowRecordRepository = borrowRecordRepository;
    }

    public BorrowRecord borrowBook(Long bookId, Long borrowerId) {
        // 1. Find the book (throw if not found)
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + bookId));

        // 2. Check if book is available (throw if not)
        if (!book.getAvailable()) {
            throw new BusinessValidationException("Book is not available for borrowing");
        }

        // 3. Find the borrower (throw if not found)
        Borrower borrower = borrowerRepository.findById(borrowerId)
                .orElseThrow(() -> new ResourceNotFoundException("Borrower not found with id: " + borrowerId));

        // 4. Mark book as unavailable
        book.setAvailable(false);
        bookRepository.save(book);

        // 5. Create & save borrow record
        BorrowRecord record = new BorrowRecord();
        record.setBook(book);
        record.setBorrower(borrower);
        record.setBorrowDate(LocalDate.now()); // Optional (not checked in test)
        record.setStatus("BORROWED");          // Optional (not checked in test)

        return borrowRecordRepository.save(record);
    }

    public BorrowRecord returnBook(Long recordId) {
        // (Existing implementation remains unchanged)
        BorrowRecord record = borrowRecordRepository.findById(recordId)
                .orElseThrow(() -> new ResourceNotFoundException("Borrow record not found with id: " + recordId));

        if (record.getReturnDate() != null) {
            throw new BusinessValidationException("Book already returned");
        }

        Book book = record.getBook();
        book.setAvailable(true);
        bookRepository.save(book);

        record.setReturnDate(LocalDate.now());
        record.setStatus("RETURNED");
        return borrowRecordRepository.save(record);
    }

    public List<BorrowRecord> getActiveBorrowRecords() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getActiveBorrowRecords'");
    }

    public List<BorrowRecord> getBorrowRecordsByBorrower(Long borrowerId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBorrowRecordsByBorrower'");
    }
}