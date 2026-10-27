package com.r2s.masterclass.student.repository;

import com.r2s.masterclass.student.entity.StudentEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/** Truy vấn gọn, không tải quan hệ không cần thiết. */
public interface StudentRepository extends JpaRepository<StudentEntity, UUID> {
    boolean existsByEmail(String email);
    boolean existsByNationalId(String nationalId);
}
