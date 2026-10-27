package com.r2s.masterclass.student.exception;

/** Báo email hoặc CCCD đã được đăng ký. */
public class StudentAlreadyExistsException extends RuntimeException {
    public StudentAlreadyExistsException(String message) { super(message); }
}
