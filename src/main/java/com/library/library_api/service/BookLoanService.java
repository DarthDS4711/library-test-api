package com.library.library_api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.library.library_api.dto.BookLoanCreateDTO;
import com.library.library_api.model.BookLoan;
import com.library.library_api.model.Inventary;
import com.library.library_api.model.Student;
import com.library.library_api.repository.BookLoanRepository;

@Service
public class BookLoanService {
    
    @Autowired
    private BookLoanRepository bookLoanRepository;

    @Autowired
    private InventaryService inventaryService;

    @Autowired
    private StudentService studentService;

    public ResponseEntity<List<BookLoan>> getLoans(){
        return ResponseEntity.ok(this.bookLoanRepository.getCurrentBookLoans());
    }

    public ResponseEntity<BookLoanCreateDTO> createLoanOfStudent(BookLoanCreateDTO bookLoanCreateDTO){
        Optional<Inventary> posibleInventary = this.inventaryService.getBookInventaryData(bookLoanCreateDTO.getBookId());
        if(!posibleInventary.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        Optional<Student> posibleStudent = this.studentService.findById(Long.valueOf(bookLoanCreateDTO.getStudentNumber()));
         if(!posibleStudent.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        Inventary inventary = posibleInventary.get();
        if(inventary.getNumberAvalable() <= 0){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        Student student = posibleStudent.get();
        Optional<BookLoan> posibleLoan = this.bookLoanRepository.getCurrentLoan(bookLoanCreateDTO.getBookId(), student.getId());
        if(posibleLoan.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(bookLoanCreateDTO);
        }
        inventary.setNumberAvalable(inventary.getNumberAvalable() - 1);
        this.inventaryService.updateInventaryAvailableBooks(inventary);
        BookLoan bookLoan = new BookLoan();
        bookLoan.setBookId(bookLoanCreateDTO.getBookId());
        bookLoan.setStudentId(student.getId());
        this.save(bookLoan);
        return ResponseEntity.ok(bookLoanCreateDTO);
    }

    public ResponseEntity<BookLoanCreateDTO> finishStudentLoan(BookLoanCreateDTO bookLoanCreateDTO){
        Optional<Inventary> posibleInventary = this.inventaryService.getBookInventaryData(bookLoanCreateDTO.getBookId());
        if(!posibleInventary.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        Optional<Student> posibleStudent = this.studentService.findById(Long.valueOf(bookLoanCreateDTO.getStudentNumber()));
         if(!posibleStudent.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        Inventary inventary = posibleInventary.get();
        Student student = posibleStudent.get();
        Optional<BookLoan> posibleLoan = this.bookLoanRepository.getCurrentLoan(bookLoanCreateDTO.getBookId(), student.getId());
        if(!posibleLoan.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(bookLoanCreateDTO);
        }
        inventary.setNumberAvalable(inventary.getNumberAvalable() + 1);
        this.inventaryService.updateInventaryAvailableBooks(inventary);
        BookLoan bookLoan = posibleLoan.get();
        bookLoan.setFinishedLoanDate(LocalDateTime.now());
        this.bookLoanRepository.update(bookLoan);
        return ResponseEntity.ok(bookLoanCreateDTO);
    }

    private void save(BookLoan bookLoan) {
        this.bookLoanRepository.save(bookLoan);
    }
}
