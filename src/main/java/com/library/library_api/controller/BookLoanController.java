package com.library.library_api.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.library_api.dto.BookLoanCreateDTO;
import com.library.library_api.model.BookLoan;
import com.library.library_api.model.Inventary;
import com.library.library_api.model.Student;
import com.library.library_api.repository.BookLoanRepository;
import com.library.library_api.repository.InventaryRepository;
import com.library.library_api.repository.StudentRepository;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/loans")
public class BookLoanController {
    
    @Autowired
    private BookLoanRepository bookLoanRepository;

    @Autowired
    private InventaryRepository inventaryRepository;

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/")
    public ResponseEntity<List<BookLoan>> getAllLoans(){
        return ResponseEntity.ok(this.bookLoanRepository.getCurrentBookLoans());
    }

    @Transactional
    @PostMapping("/initloan")
    public ResponseEntity<BookLoanCreateDTO> initLoan(@RequestBody @Valid BookLoanCreateDTO bookLoanCreateDTO){
        Optional<Inventary> posibleInventary = this.inventaryRepository.getInventaryBook(bookLoanCreateDTO.getBookId());
        if(!posibleInventary.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        Optional<Student> posibleStudent = this.studentRepository.findById(Long.valueOf(bookLoanCreateDTO.getStudentNumber()));
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
        this.inventaryRepository.update(inventary.getNumberAvalable(), inventary.getId());
        BookLoan bookLoan = new BookLoan();
        bookLoan.setBookId(bookLoanCreateDTO.getBookId());
        bookLoan.setStudentId(student.getId());
        this.bookLoanRepository.save(bookLoan);
        return ResponseEntity.ok(bookLoanCreateDTO);

    }


    @Transactional
    @PutMapping("/finishloan")
    public ResponseEntity<BookLoanCreateDTO> finishedLoan(@RequestBody @Valid BookLoanCreateDTO bookLoanCreateDTO){
        Optional<Inventary> posibleInventary = this.inventaryRepository.getInventaryBook(bookLoanCreateDTO.getBookId());
        if(!posibleInventary.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        Optional<Student> posibleStudent = this.studentRepository.findById(Long.valueOf(bookLoanCreateDTO.getStudentNumber()));
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
        this.inventaryRepository.update(inventary.getNumberAvalable(), inventary.getId());
        BookLoan bookLoan = posibleLoan.get();
        bookLoan.setFinishedLoanDate(LocalDateTime.now());
        this.bookLoanRepository.update(bookLoan);
        return ResponseEntity.ok(bookLoanCreateDTO);
    
    }
}
