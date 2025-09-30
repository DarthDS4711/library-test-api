package com.library.library_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.library.library_api.dto.StudentDTO;
import com.library.library_api.model.Student;
import com.library.library_api.repository.StudentRepository;

@Service
public class StudentService {
    
    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getStudents(){
        return this.studentRepository.findAll();
    }

    public ResponseEntity<StudentDTO> saveStudent(StudentDTO studentDTO){
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

    public Optional<Student> findById(Long id){
        return this.studentRepository.findById(Long.valueOf(id));
    }
}
