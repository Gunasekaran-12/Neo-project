package com.examly.springapp.service;

import com.examly.springapp.entity.BorrowRecord;
import com.examly.springapp.entity.Borrower;
import com.examly.springapp.exception.BusinessValidationException;
import com.examly.springapp.exception.ResourceNotFoundException;
import com.examly.springapp.repository.BookRepository;
import com.examly.springapp.repository.BorrowRecordRepository;
import com.examly.springapp.repository.BorrowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BorrowService {

    @Autowired
private BorrowRecordRepository borrowRecordRepository;

@Autowired
private BookRepository bookRepository;

@Autowired
private BorrowerRepository borrowerRepository;
      
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
      
      public BorrowRecord returnBook(Long recordId) {
          BorrowRecord record = borrowRecordRepository.findById(recordId)
                  .orElseThrow(() -> new ResourceNotFoundException("Borrow record not found"));
      
          if (record.isReturned()) {
              throw new BusinessValidationException("Book already returned");
          }
      
          record.setReturnDate(LocalDate.now());
          record.setReturned(true);
      
          Book book = record.getBook();
          book.setAvailable(true);
          bookRepository.save(book);
      
          return borrowRecordRepository.save(record);
      }
      
      public Optional<BorrowRecord> getBorrowRecordById(Long id) {
          return borrowRecordRepository.findById(id);
      }
      
      public BorrowRecord saveBorrowRecord(BorrowRecord borrowRecord) {
          return borrowRecordRepository.save(borrowRecord);
      }
      
      public BorrowRecord borrowBook(Long bookId, Long borrowerId) {
          Book book = bookRepository.findById(bookId)
                  .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
      
          if (!book.isAvailable()) {
              throw new BusinessValidationException("Book is not available for borrowing");
          }
      
          Borrower borrower = borrowerRepository.findById(borrowerId)
                  .orElseThrow(() -> new ResourceNotFoundException("Borrower not found"));
      
          book.setAvailable(false);
          bookRepository.save(book);
      
          BorrowRecord record = new BorrowRecord();
          record.setBook(book);
          record.setBorrower(borrower);
          record.setBorrowDate(LocalDate.now());
          record.setDueDate(LocalDate.now().plusDays(14)); // Example due date: 2 weeks
          record.setReturned(false);
      
          return borrowRecordRepository.save(record);
      }
    
}