package com.examly.springapp.controller;

import com.examly.springapp.entity.Book;
import com.examly.springapp.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.ActiveProfiles;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
    }

    @Test
    void testAddAndGetBook() throws Exception {
        Book book = new Book();
        book.setTitle("TestTitle");
        book.setAuthor("TestAuthor");
        book.setIsbn("1234567890432");
        book.setPublicationYear(2012);
        String json = objectMapper.writeValueAsString(book);
        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("TestTitle"));
        Book inserted = bookRepository.findByIsbn("1234567890432").orElse(null);
        assert inserted != null;
        mockMvc.perform(get("/api/books/" + inserted.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("TestTitle"));
    }

    @Test
    void testBookNotFoundReturns404() throws Exception {
        mockMvc.perform(get("/api/books/99999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Book not found"));
    }

    @Test
    void testValidationErrorReturns400() throws Exception {
        Book book = new Book();
        book.setTitle("");
        book.setAuthor("");
        book.setIsbn("shortisbn");
        book.setPublicationYear(950);
        String json = objectMapper.writeValueAsString(book);
        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }
}
