package com.r2s.masterclass.student.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.Instant;
import java.util.UUID;

/** Thực thể lưu hồ sơ OJT nội bộ, không trả trực tiếp qua REST API. */
@Entity
@Table(name = "ojt_students", uniqueConstraints = {
        @UniqueConstraint(name = "uk_ojt_students_email", columnNames = "email"),
        @UniqueConstraint(name = "uk_ojt_students_national_id", columnNames = "national_id")
})
public class StudentEntity {
    @Id
    private UUID id;

    @Column(name = "full_name", nullable = false, length = 150)
    private String fullName;

    @Column(nullable = false, length = 254)
    private String email;

    @Column(nullable = false, length = 10)
    private String phone;

    @Column(name = "national_id", nullable = false, length = 12)
    private String nationalId;

    @Column(length = 200)
    private String university;

    @Column(length = 150)
    private String major;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private StudentStatus status = StudentStatus.PENDING_APPROVAL;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    protected StudentEntity() { }

    public StudentEntity(String fullName, String email, String phone, String nationalId,
                         String university, String major) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.nationalId = nationalId;
        this.university = university;
        this.major = major;
    }

    @PrePersist
    void prepareForInsert() {
        if (id == null) id = UUID.randomUUID();
        if (status == null) status = StudentStatus.PENDING_APPROVAL;
        if (createdAt == null) createdAt = Instant.now();
    }

    public UUID getId() { return id; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getNationalId() { return nationalId; }
    public String getUniversity() { return university; }
    public String getMajor() { return major; }
    public StudentStatus getStatus() { return status; }
    public Instant getCreatedAt() { return createdAt; }
}
