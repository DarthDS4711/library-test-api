package com.library.library_api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.library_api.model.Inventary;
import com.library.library_api.repository.InventaryRepository;

@RestController
@RequestMapping("/api/vi/inventary")
public class InventaryController {
    
    @Autowired
    private InventaryRepository inventaryRepository;

    @GetMapping("/")
    public ResponseEntity<List<Inventary>> getAllInventaries(){
        return ResponseEntity.ok(this.inventaryRepository.getAll());
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<Inventary> getBookInventary(@PathVariable Long bookId){
        Optional<Inventary> posibleInventary = this.inventaryRepository.getInventaryBook(bookId);
        if(!posibleInventary.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(posibleInventary.get());
    }
}
