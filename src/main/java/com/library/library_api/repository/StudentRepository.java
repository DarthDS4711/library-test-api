package com.library.library_api.repository;



import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.library.library_api.model.Student;


@Mapper
public interface StudentRepository{

    @Select("SELECT * WHERE NAME = #{name}")
    Optional<Student> findByName(String name);

    @Select("SELECT * FROM STUDENT WHERE STUDENT_NUMBER = #{code}")
    Optional<Student> findByCode(int code);

    @Select("SELECT * FROM STUDENT")
    List<Student> findAll();

    @Insert("INSERT INTO STUDENT(NAME, STUDENT_NUMBER) VALUES(#{name}, #{studentNumber})")
    void save(Student student);
    
}
