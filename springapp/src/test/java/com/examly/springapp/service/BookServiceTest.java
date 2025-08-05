package com.examly.springapp.service;

import com.examly.springapp.entity.Book;
import com.examly.springapp.exception.BusinessValidationException;
import com.examly.springapp.repository.BookRepository;
import com.examly.springapp.repository.BorrowRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceTest {
    @Mock
    private BookRepository bookRepository;

    @Mock
    private BorrowRecordRepository borrowRecordRepository;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBookCreation() {
        Book book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("Eric");
        book.setIsbn("1234567890123");
        book.setPublicationYear(2020);
        when(bookRepository.findByIsbn(book.getIsbn())).thenReturn(Optional.empty());
        when(bookRepository.save(ArgumentMatchers.any(Book.class))).thenAnswer(i -> i.getArgument(0));
        Book saved = bookService.addBook(book);
        assertEquals("Test Book", saved.getTitle());
        assertEquals("Eric", saved.getAuthor());
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void testDuplicateIsbnThrowsValidation() {
        Book book = new Book();
        book.setTitle("Book 1");
        book.setAuthor("John");
        book.setIsbn("1234567890123");
        book.setPublicationYear(2021);
        when(bookRepository.findByIsbn("1234567890123")).thenReturn(Optional.of(book));
        BusinessValidationException ex = assertThrows(BusinessValidationException.class, () -> bookService.addBook(book));
        assertEquals("ISBN already exists", ex.getMessage());
    }
}
