package com.library.library_api.repository;



import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.library.library_api.model.Category;

@Mapper
public interface CategoryRepository{

    @Select("SELECT * FROM CATEGORY")
    List<Category> findAll();

    @Insert("INSERT INTO CATEGORY(name) VALUES (#{name})")
    void save(Category category);

    @Select("SELECT * FROM CATEGORY WHERE ID = #{id}")
    Optional<Category> findById(Long id);
}
