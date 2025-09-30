package com.library.library_api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.library_api.dto.category.CategoryDTO;
import com.library.library_api.model.Category;
import com.library.library_api.repository.CategoryRepository;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    
    @Autowired
    private CategoryRepository repository;
    

    @GetMapping("/")
    public ResponseEntity<List<Category>> getAllCategories(){
        List<Category> categories = this.repository.findAll();
        return ResponseEntity.ok(categories);
    }

    @PostMapping("/")
    public ResponseEntity<CategoryDTO> saveCategory(@RequestBody @Valid CategoryDTO categoryDTO){
        Category category = new Category();
        category.setName(categoryDTO.getName());
        this.repository.save(category);
        return ResponseEntity.ok(categoryDTO);
    }
}
