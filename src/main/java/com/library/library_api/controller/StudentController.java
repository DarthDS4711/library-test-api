package com.library.library_api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.library_api.dto.StudentDTO;
import com.library.library_api.model.Student;
import com.library.library_api.service.StudentService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/student")
public class StudentController {
    
    @Autowired
    private StudentService studentService;

    @GetMapping("/")
    public ResponseEntity<List<Student>> getAllStudents(){
        return ResponseEntity.ok(this.studentService.getStudents());
    }

    @PostMapping("/")
    public ResponseEntity<StudentDTO> saveStudent(@RequestBody @Valid StudentDTO studentDTO){
        return this.studentService.saveStudent(studentDTO);
    }
}
