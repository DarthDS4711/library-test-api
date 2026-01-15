package com.library.library_api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.library_api.controller.BookLoanController;
import com.library.library_api.dto.BookLoanCreateDTO;
import com.library.library_api.model.BookLoan;
import com.library.library_api.service.BookLoanService;

@WebMvcTest(BookLoanController.class)
public class BookLoanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookLoanService bookLoanService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllLoans_ShouldReturnList() throws Exception {
        when(bookLoanService.getLoans()).thenReturn(ResponseEntity.ok(Collections.emptyList()));

        mockMvc.perform(get("/api/v1/loans/"))
                .andExpect(status().isOk());
    }

    @Test
    void initLoan_ShouldReturnCreatedLoan() throws Exception {
        BookLoanCreateDTO dto = new BookLoanCreateDTO();
        dto.setBookId(1L);
        dto.setStudentNumber(1); 
        // Asegúrate de que tu DTO tenga los campos necesarios para pasar @Valid si tienes anotaciones

        when(bookLoanService.createLoanOfStudent(any(BookLoanCreateDTO.class)))
            .thenReturn(ResponseEntity.ok(dto));

        mockMvc.perform(post("/api/v1/loans/initloan")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void finishedLoan_ShouldReturnUpdatedLoan() throws Exception {
        BookLoanCreateDTO dto = new BookLoanCreateDTO();
        dto.setBookId(1L);
        dto.setStudentNumber(1); 
        
        when(bookLoanService.finishStudentLoan(any(BookLoanCreateDTO.class)))
            .thenReturn(ResponseEntity.ok(dto));

        mockMvc.perform(put("/api/v1/loans/finishloan")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }
}