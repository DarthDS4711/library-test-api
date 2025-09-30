package com.library.library_api.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.library_api.dto.StudentDTO;
import com.library.library_api.model.Student;
import com.library.library_api.repository.StudentRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/student")
public class StudentController {
    
    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/")
    public ResponseEntity<List<Student>> getAllStudents(){
        return ResponseEntity.ok(this.studentRepository.findAll());
    }

    @PostMapping("/")
    public ResponseEntity<StudentDTO> saveStudent(@RequestBody @Valid StudentDTO studentDTO){
        Student student = new Student();
        student.setName(studentDTO.getName());
        student.setStudentNumber(studentDTO.getStudentNumber());
        Optional<Student> posibleStudent = this.studentRepository.findByCode(studentDTO.getStudentNumber());
        if(posibleStudent.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        this.studentRepository.save(student);
        return ResponseEntity.ok(studentDTO);
    }
}
