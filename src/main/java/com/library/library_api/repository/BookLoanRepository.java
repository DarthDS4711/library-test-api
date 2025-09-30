package com.library.library_api.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.library.library_api.model.BookLoan;

@Mapper
public interface BookLoanRepository{
    
    @Select("SELECT * FROM BOOK_LOAN WHERE IS_RETURNED = 0")
    List<BookLoan> getCurrentBookLoans();

    @Select("SELECT * FROM BOOK_LOAN WHERE BOOK_ID = #{bookId} AND STUDENT_ID = #{studentId} AND IS_RETURNED = 0")
    Optional<BookLoan> getCurrentLoan(Long bookId, Long studentId);

    @Insert("INSERT INTO BOOK_LOAN(STUDENT_ID, BOOK_ID) VALUES(#{studentId}, #{bookId})")
    void save(BookLoan bookLoan);

    @Update("UPDATE BOOK_LOAN SET IS_RETURNED = 1, FINISHED_LOAN_DATE = #{finishedLoanDate} WHERE ID = #{id}")
    int update(BookLoan bookLoan);
}
