package com.library.library_api.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.library.library_api.model.Book;

@Mapper
public interface BookRepository {

    @Select("SELECT * FROM BOOK")
    List<Book> findAll();

    @Select("SELECT * FROM BOOK WHERE CATEGORY_ID = #{categoryId}")
    List<Book> findByCategoryId(Long categoryId);

    @Insert("INSERT INTO BOOK(DESCRIPTION, NAME, CATEGORY_ID) VALUES(#{name}, #{description}, #{categoryId})")
    void save(Book book);

    @Select("SELECT MAX(id) FROM BOOK")
    Long getInsertedId();
}
