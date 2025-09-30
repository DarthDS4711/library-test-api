package com.library.library_api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.library_api.dto.CategoryDTO;
import com.library.library_api.model.Category;
import com.library.library_api.service.CategoryService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    

    @GetMapping("/")
    public ResponseEntity<List<Category>> getAllCategories(){
        List<Category> categories = this.categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @PostMapping("/")
    public ResponseEntity<CategoryDTO> saveCategory(@RequestBody @Valid CategoryDTO categoryDTO){
        return ResponseEntity.ok(this.categoryService.saveCategory(categoryDTO));
    }
}
