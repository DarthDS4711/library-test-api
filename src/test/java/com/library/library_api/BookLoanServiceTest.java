package com.library.library_api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.library.library_api.dto.BookLoanCreateDTO;
import com.library.library_api.model.BookLoan;
import com.library.library_api.model.Inventary;
import com.library.library_api.model.Student;
import com.library.library_api.repository.BookLoanRepository;
import com.library.library_api.service.BookLoanService;
import com.library.library_api.service.InventaryService;
import com.library.library_api.service.StudentService;

@ExtendWith(MockitoExtension.class)
public class BookLoanServiceTest {

    @Mock
    private BookLoanRepository bookLoanRepository;
    @Mock
    private InventaryService inventaryService;
    @Mock
    private StudentService studentService;

    @InjectMocks
    private BookLoanService bookLoanService;

    @Test
    void createLoanOfStudent_WhenInventaryNotFound_ReturnsNotFound() {
        BookLoanCreateDTO dto = new BookLoanCreateDTO();
        dto.setBookId(1L);

        when(inventaryService.getBookInventaryData(1L)).thenReturn(Optional.empty());

        ResponseEntity<BookLoanCreateDTO> response = bookLoanService.createLoanOfStudent(dto);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void createLoanOfStudent_WhenNoCopiesAvailable_ReturnsConflict() {
        BookLoanCreateDTO dto = new BookLoanCreateDTO();
        dto.setBookId(1L);
        dto.setStudentNumber(100);

        Inventary inv = new Inventary();
        inv.setNumberAvalable(0); // Sin copias

        Student student = new Student();
        student.setId(10L);

        when(inventaryService.getBookInventaryData(1L)).thenReturn(Optional.of(inv));
        when(studentService.findById(100L)).thenReturn(Optional.of(student));

        ResponseEntity<BookLoanCreateDTO> response = bookLoanService.createLoanOfStudent(dto);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        verify(bookLoanRepository, never()).save(any());
    }

    @Test
    void createLoanOfStudent_Success() {
        // Arrange
        BookLoanCreateDTO dto = new BookLoanCreateDTO();
        dto.setBookId(1L);
        dto.setStudentNumber(100);

        Inventary inv = new Inventary();
        inv.setNumberAvalable(5);

        Student student = new Student();
        student.setId(10L);

        when(inventaryService.getBookInventaryData(1L)).thenReturn(Optional.of(inv));
        when(studentService.findById(100L)).thenReturn(Optional.of(student));
        when(bookLoanRepository.getCurrentLoan(1L, 10L)).thenReturn(Optional.empty()); // No tiene prestamos activos

        // Act
        ResponseEntity<BookLoanCreateDTO> response = bookLoanService.createLoanOfStudent(dto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(inventaryService).updateInventaryAvailableBooks(inv); // Verifica que restó inventario
        verify(bookLoanRepository).save(any(BookLoan.class)); // Verifica que guardó el prestamo
    }
}