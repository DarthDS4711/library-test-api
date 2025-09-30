package com.library.library_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.library_api.dto.CategoryDTO;
import com.library.library_api.model.Category;
import com.library.library_api.repository.CategoryRepository;

@Service
public class CategoryService {
    
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories(){
        return this.categoryRepository.findAll();
    }

    public CategoryDTO saveCategory(CategoryDTO categoryDTO){
        Category category = new Category();
        category.setName(categoryDTO.getName());
        this.categoryRepository.save(category);
        return categoryDTO;
    }

    public Optional<Category> findById(Long id){
        return this.categoryRepository.findById(id);
    }
}
