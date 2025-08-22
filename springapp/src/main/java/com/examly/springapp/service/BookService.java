package com.examly.springapp.service;

import com.examly.springapp.entity.Book;
import com.examly.springapp.exception.BusinessValidationException;
import com.examly.springapp.exception.ResourceNotFoundException;
import com.examly.springapp.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public Book addBook(Book book) {
        // Validate ISBN presence
        if (book.getIsbn() == null || book.getIsbn().trim().isEmpty()) {
            throw new BusinessValidationException("ISBN is required");
        }
        
        // Check for duplicate ISBN using both methods for backward compatibility
        if (bookRepository.existsByIsbn(book.getIsbn()) || 
            bookRepository.findByIsbn(book.getIsbn()).isPresent()) {
            throw new BusinessValidationException("ISBN already exists");
        }
        
        return bookRepository.save(book);
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book updateBook(Long id, Book bookDetails) {
        Book existingBook = getBookById(id);
        
        if (!existingBook.getIsbn().equals(bookDetails.getIsbn())) {
            throw new BusinessValidationException("ISBN cannot be changed");
        }
        
        existingBook.setTitle(bookDetails.getTitle());
        existingBook.setAuthor(bookDetails.getAuthor());
        existingBook.setPublicationYear(bookDetails.getPublicationYear());
        existingBook.setAvailable(bookDetails.getAvailable());
        
        return bookRepository.save(existingBook);
    }

    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }
}