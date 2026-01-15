package com.library.library_api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.library_api.controller.BookController;
import com.library.library_api.dto.BookDTO;
import com.library.library_api.model.Book;
import com.library.library_api.service.BookService;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getBooks_ShouldReturnListOfBooks() throws Exception {
        // Arrange
        Book book = new Book();
        book.setId(1L);
        book.setName("Clean Code");
        
        // El servicio devuelve una List<Book> pura
        when(bookService.getAllBooks()).thenReturn(Collections.singletonList(book));

        // Act & Assert
        mockMvc.perform(get("/api/v1/book/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Clean Code"));
    }

    @Test
    void saveBook_ShouldReturnSavedBookDTO() throws Exception {
        // Arrange
        BookDTO bookDTO = new BookDTO();
        bookDTO.setDescription("Des");
        bookDTO.setName("Spring Boot in Action");
        bookDTO.setCategoryId(1L);
        bookDTO.setAvaliableBooks(5);

        // El servicio devuelve ResponseEntity<BookDTO>
        when(bookService.saveBook(any(BookDTO.class))).thenReturn(ResponseEntity.ok(bookDTO));

        // Act & Assert
        mockMvc.perform(post("/api/v1/book/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Spring Boot in Action"));
    }
}