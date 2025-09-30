package com.library.library_api.controller;

import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.library.library_api.service.BookLoanService;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/loans")
public class BookLoanController {

    @Autowired
    private BookLoanService bookLoanService;


    @GetMapping("/")
    public ResponseEntity<List<BookLoan>> getAllLoans(){
        return this.bookLoanService.getLoans();
    }

    @Transactional
    @PostMapping("/initloan")
    public ResponseEntity<BookLoanCreateDTO> initLoan(@RequestBody @Valid BookLoanCreateDTO bookLoanCreateDTO){
        return this.bookLoanService.createLoanOfStudent(bookLoanCreateDTO);

    }


    @Transactional
    @PutMapping("/finishloan")
    public ResponseEntity<BookLoanCreateDTO> finishedLoan(@RequestBody @Valid BookLoanCreateDTO bookLoanCreateDTO){
        return this.bookLoanService.finishStudentLoan(bookLoanCreateDTO);
    
    }
}
