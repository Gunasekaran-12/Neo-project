package com.examly.springapp.service;

import com.examly.springapp.entity.Book;
import com.examly.springapp.exception.BusinessValidationException;
import com.examly.springapp.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book addBook(Book book) {
        // Validate ISBN uniqueness
        String isbn = book.getIsbn();
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new BusinessValidationException("ISBN cannot be null or empty");
        }

        bookRepository.findByIsbn(isbn)
            .ifPresent(existingBook -> {
                throw new BusinessValidationException("ISBN already exists");
            });

        return bookRepository.save(book);
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }
}