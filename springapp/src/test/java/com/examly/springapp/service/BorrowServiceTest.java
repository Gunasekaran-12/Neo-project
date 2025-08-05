package com.examly.springapp.service;

import com.examly.springapp.entity.Book;
import com.examly.springapp.entity.BorrowRecord;
import com.examly.springapp.entity.Borrower;
import com.examly.springapp.exception.BusinessValidationException;
import com.examly.springapp.exception.ResourceNotFoundException;
import com.examly.springapp.repository.BookRepository;
import com.examly.springapp.repository.BorrowRecordRepository;
import com.examly.springapp.repository.BorrowerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BorrowServiceTest {
    @Mock
    private BookRepository bookRepository;
    @Mock
    private BorrowerRepository borrowerRepository;
    @Mock
    private BorrowRecordRepository borrowRecordRepository;
    @InjectMocks
    private BorrowService borrowService;
    @BeforeEach
    void setUp() { MockitoAnnotations.openMocks(this); }

    @Test
    void testBorrowBookSuccess() {
        Book book = new Book();
        book.setId(1L);
        book.setAvailable(true);
        Borrower borrower = new Borrower();
        borrower.setId(2L);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(borrowerRepository.findById(2L)).thenReturn(Optional.of(borrower));
        when(borrowRecordRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        BorrowRecord result = borrowService.borrowBook(1L, 2L);
        assertFalse(book.getAvailable());
        assertNotNull(result);
        assertEquals(book, result.getBook());
        assertEquals(borrower, result.getBorrower());
    }

    @Test
    void testBorrowBookNotAvailable() {
        Book book = new Book();
        book.setId(1L);
        book.setAvailable(false);
        Borrower borrower = new Borrower();
        borrower.setId(2L);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(borrowerRepository.findById(2L)).thenReturn(Optional.of(borrower));
        BusinessValidationException ex = assertThrows(BusinessValidationException.class, () -> borrowService.borrowBook(1L, 2L));
        assertEquals("Book is not available for borrowing", ex.getMessage());
    }
    
    @Test
    void testReturnBookSuccess() {
        Book book = new Book();
        book.setId(5L);
        book.setAvailable(false);
        Borrower borrower = new Borrower();
        borrower.setId(3L);
        BorrowRecord br = new BorrowRecord();
        br.setId(50L);
        br.setBook(book);
        br.setBorrower(borrower);
        br.setReturnDate(null);
        when(borrowRecordRepository.findById(50L)).thenReturn(Optional.of(br));
        when(bookRepository.save(book)).thenAnswer(i -> i.getArgument(0));
        when(borrowRecordRepository.save(br)).thenReturn(br);
        BorrowRecord res = borrowService.returnBook(50L);
        assertNotNull(res.getReturnDate());
        assertTrue(book.getAvailable());
    }
    
    @Test
    void testReturnBookAlreadyReturned() {
        Book book = new Book();
        book.setAvailable(true);
        Borrower borrower = new Borrower();
        BorrowRecord br = new BorrowRecord();
        br.setBook(book);
        br.setBorrower(borrower);
        br.setReturnDate(java.time.LocalDate.now());
        when(borrowRecordRepository.findById(11L)).thenReturn(Optional.of(br));
        BusinessValidationException ex = assertThrows(BusinessValidationException.class, () -> borrowService.returnBook(11L));
        assertEquals("Book already returned", ex.getMessage());
    }
}
