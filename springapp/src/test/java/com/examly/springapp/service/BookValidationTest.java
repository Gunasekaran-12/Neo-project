package com.examly.springapp.service;

import com.examly.springapp.entity.Book;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

public class BookValidationTest {
    private static Validator validator;
    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    @Test
    public void testBookRequiredFields() {
        Book book = new Book();
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertFalse(violations.isEmpty());
        boolean isbnInvalid = violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("isbn"));
        assertTrue(isbnInvalid);
    }
    @Test
    public void testBookFieldValidations() {
        Book book = new Book();
        book.setTitle("");
        book.setAuthor("A");
        book.setIsbn("abc");
        book.setPublicationYear(500);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("isbn")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("title")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("publicationYear")));
    }
}
