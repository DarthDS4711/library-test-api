package com.library.library_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.library.library_api.model.Inventary;
import com.library.library_api.repository.InventaryRepository;

@Service
public class InventaryService {
    
    @Autowired
    private InventaryRepository inventaryRepository;

    public ResponseEntity<List<Inventary>> getAllInventaries(){
        return ResponseEntity.ok(this.inventaryRepository.getAll());
    }

    public ResponseEntity<Inventary> getBookInventary(Long bookId){
        Optional<Inventary> posibleInventary = this.inventaryRepository.getInventaryBook(bookId);
        if(!posibleInventary.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(posibleInventary.get());
    }

    public void saveInventary(Inventary inventary){
        this.inventaryRepository.save(inventary);
    }

    public Optional<Inventary> getBookInventaryData(Long bookId){
        return this.inventaryRepository.getInventaryBook(bookId);
    }

    public void updateInventaryAvailableBooks(Inventary inventary){
        this.inventaryRepository.update(inventary.getNumberAvalable(), inventary.getId());
    }
}
