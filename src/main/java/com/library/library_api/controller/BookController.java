package com.library.library_api.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.library_api.dto.BookDTO;
import com.library.library_api.model.Book;
import com.library.library_api.model.Category;
import com.library.library_api.model.Inventary;
import com.library.library_api.repository.BookRepository;
import com.library.library_api.repository.CategoryRepository;
import com.library.library_api.repository.InventaryRepository;


@RestController
@RequestMapping("/api/v1/book")
public class BookController {


    @Autowired
    private BookRepository bookRepository;


    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private InventaryRepository inventaryRepository;

    @GetMapping("/")
    public ResponseEntity<List<Book>> getBooks(){
        return ResponseEntity.ok(this.bookRepository.findAll());
    }

    //TODO: Pendiente realizar el controller de prestamo, el de inventario y el de estudiante
    //TODO: Cada uno con agregar y eliminado
    //TODO: El de inventario es solo para consulta de informacion en especial
    //TODO: El de prestamo va a guardar el prestamo y restara al inventario
    //TODO: Cuando se devuelva el libro pondremos fecha de devolucion y agregaremos +1 en el inventario

    @PostMapping("/")
    @Transactional
    public ResponseEntity<BookDTO> saveBook(@RequestBody @Valid BookDTO bookDTO){
        Book book = new Book();
        Optional<Category> cOptional = this.categoryRepository.findById(bookDTO.getCategoryId());
        if(!cOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(bookDTO);
        }
        Category category = cOptional.get();
        book.setCategoryId(category.getId());
        book.setDescription(bookDTO.getDescription());
        book.setName(bookDTO.getName());
        this.bookRepository.save(book);
        Long lastInsertedId = this.bookRepository.getInsertedId();
        Inventary inventary = new Inventary();
        inventary.setBookId(lastInsertedId);
        inventary.setNumberAvalable(bookDTO.getAvaliableBooks());
        this.inventaryRepository.save(inventary);
        return ResponseEntity.ok(bookDTO);
    }
    
}
