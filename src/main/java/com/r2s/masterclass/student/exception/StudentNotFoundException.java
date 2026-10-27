package com.r2s.masterclass.student.exception;

/** Báo không tìm thấy hồ sơ theo mã sinh viên. */
public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(String message) { super(message); }
}
