package com.r2s.masterclass.student.dto;

import com.r2s.masterclass.student.entity.StudentEntity;
import com.r2s.masterclass.student.entity.StudentStatus;
import java.util.UUID;

/** DTO phản hồi có che dữ liệu PII trước khi gửi cho Client. */
public record StudentResponseDTO(
        UUID id,
        String fullName,
        String email,
        String maskedPhone,
        String maskedNationalId,
        String university,
        String major,
        StudentStatus status
) {
    public static StudentResponseDTO from(StudentEntity student) {
        return new StudentResponseDTO(
                student.getId(), student.getFullName(), student.getEmail(),
                mask(student.getPhone(), 3, 2), mask(student.getNationalId(), 3, 3),
                student.getUniversity(), student.getMajor(), student.getStatus());
    }

    private static String mask(String value, int visiblePrefix, int visibleSuffix) {
        if (value == null || value.isBlank()) return value;
        if (value.length() <= visiblePrefix + visibleSuffix) return "*".repeat(value.length());
        return value.substring(0, visiblePrefix)
                + "*".repeat(value.length() - visiblePrefix - visibleSuffix)
                + value.substring(value.length() - visibleSuffix);
    }
}
