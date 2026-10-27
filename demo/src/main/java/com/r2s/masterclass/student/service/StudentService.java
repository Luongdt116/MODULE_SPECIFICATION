package com.r2s.masterclass.student.service;

import com.r2s.masterclass.student.dto.StudentRequestDTO;
import com.r2s.masterclass.student.dto.StudentResponseDTO;
import java.util.UUID;

/** Hợp đồng nghiệp vụ đăng ký và tra cứu sinh viên OJT. */
public interface StudentService {
    StudentResponseDTO register(StudentRequestDTO request);
    StudentResponseDTO getById(UUID id);
}
