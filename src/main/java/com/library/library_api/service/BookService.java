package com.library.library_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.library.library_api.dto.BookDTO;
import com.library.library_api.model.Book;
import com.library.library_api.model.Category;
import com.library.library_api.model.Inventary;
import com.library.library_api.repository.BookRepository;

@Service
public class BookService {
    
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private InventaryService inventaryService;

    public List<Book> getAllBooks(){
        return this.bookRepository.findAll();
    }

    public ResponseEntity<BookDTO> saveBook(BookDTO bookDTO){
        Book book = new Book();
        Optional<Category> cOptional = this.categoryService.findById(bookDTO.getCategoryId());
        if(!cOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(bookDTO);
        }
        Category category = cOptional.get();
        book.setCategoryId(category.getId());
        book.setDescription(bookDTO.getDescription());
        book.setName(bookDTO.getName());
        this.bookRepository.save(book);
        Long lastInsertedId = this.getLastestIdAdded();
        Inventary inventary = new Inventary();
        inventary.setBookId(lastInsertedId);
        inventary.setNumberAvalable(bookDTO.getAvaliableBooks());
        this.inventaryService.saveInventary(inventary);
        return ResponseEntity.ok(bookDTO);
    }

    private Long getLastestIdAdded(){
        return this.bookRepository.getInsertedId();
    }
}
