package com.library.library_api.controller;

import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.library_api.dto.BookDTO;
import com.library.library_api.model.Book;
import com.library.library_api.service.BookService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/")
    public ResponseEntity<List<Book>> getBooks(){
        return ResponseEntity.ok(this.bookService.getAllBooks());
    }

    
    @PostMapping("/")
    @Transactional
    public ResponseEntity<BookDTO> saveBook(@RequestBody @Valid BookDTO bookDTO){
        return this.bookService.saveBook(bookDTO);
    }
    
}
