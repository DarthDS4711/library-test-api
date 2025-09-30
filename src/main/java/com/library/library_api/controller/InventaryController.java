package com.library.library_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.library_api.model.Inventary;
import com.library.library_api.service.InventaryService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/inventary")
public class InventaryController {

    @Autowired
    private InventaryService service;

    @GetMapping("/")
    public ResponseEntity<List<Inventary>> getAllInventaries(){
        return this.service.getAllInventaries();
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<Inventary> getBookInventary(@PathVariable Long bookId){
        return this.service.getBookInventary(bookId);
    }
}
