package com.r2s.masterclass.student.controller;

import com.r2s.masterclass.student.dto.StudentRequestDTO;
import com.r2s.masterclass.student.dto.StudentResponseDTO;
import com.r2s.masterclass.student.service.StudentService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** API chỉ nhận và trả về DTO, không để lộ JPA entity. */
@RestController
@RequestMapping("/api/v1/students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/register")
    public ResponseEntity<StudentResponseDTO> register(@Valid @RequestBody StudentRequestDTO request) {
        StudentResponseDTO response = studentService.register(request);
        return ResponseEntity.created(URI.create("/api/v1/students/" + response.id())).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(studentService.getById(id));
    }
}
