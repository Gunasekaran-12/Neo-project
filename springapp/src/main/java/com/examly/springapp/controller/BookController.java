package com.examly.springapp.controller;
import com.examly.springapp.entity.Book;
import com.examly.springapp.exception.BusinessValidationException;
import com.examly.springapp.service.BookService;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/books")
@Validated
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    public ResponseEntity<?> addBook(@Valid @RequestBody Book book, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(
                result.getFieldErrors().stream()
                    .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage
                    ))
            );
        }
        try {
            return ResponseEntity.ok(bookService.addBook(book));
        } catch (BusinessValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Long id) {
        return bookService.getBookById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

}