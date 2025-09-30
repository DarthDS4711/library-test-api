package com.library.library_api.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.library.library_api.model.Inventary;

@Mapper
public interface InventaryRepository{
    
    @Select("SELECT * FROM INVENTARY")
    List<Inventary> getAll();

    @Select("SELECT * FROM INVENTARY WHERE BOOK_ID = #{bookId}")
    Optional<Inventary> getInventaryBook(Long bookId);

    @Insert("INSERT INTO INVENTARY(NUMBER_AVALABLE, BOOK_ID) VALUES(#{numberAvalable}, #{bookId})")
    void save(Inventary inventary);

    @Update("UPDATE INVENTARY SET NUMBER_AVALABLE = #{numberAvailable} WHERE ID = #{id}")
    int update(int numberAvailable, Long id);
}
