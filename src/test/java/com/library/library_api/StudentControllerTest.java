package com.library.library_api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import com.library.library_api.controller.StudentController;
import com.library.library_api.dto.StudentDTO;
import com.library.library_api.service.StudentService;

@WebMvcTest(StudentController.class)
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllStudents_ShouldReturnList() throws Exception {
        when(studentService.getStudents()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/student/"))
                .andExpect(status().isOk());
    }

    @Test
    void saveStudent_ShouldReturnDTO() throws Exception {
        StudentDTO dto = new StudentDTO();
        dto.setName("Juan Perez");
        dto.setStudentNumber(1);

        when(studentService.saveStudent(any(StudentDTO.class))).thenReturn(ResponseEntity.ok(dto));

        mockMvc.perform(post("/api/v1/student/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }
}