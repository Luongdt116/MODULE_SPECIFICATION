package com.r2s.masterclass.student.service;

import com.r2s.masterclass.student.dto.StudentRequestDTO;
import com.r2s.masterclass.student.dto.StudentResponseDTO;
import com.r2s.masterclass.student.entity.StudentEntity;
import com.r2s.masterclass.student.exception.StudentAlreadyExistsException;
import com.r2s.masterclass.student.exception.StudentNotFoundException;
import com.r2s.masterclass.student.repository.StudentRepository;
import java.util.Locale;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/** Điều phối nghiệp vụ; chuyển entity thành DTO ngay tại tầng service. */
@Service
@Transactional
public class StudentServiceImpl implements StudentService {
    private final StudentRepository repository;

    public StudentServiceImpl(StudentRepository repository) {
        this.repository = repository;
    }

    @Override
    public StudentResponseDTO register(StudentRequestDTO request) {
        if (request == null) throw new IllegalArgumentException("Thông tin đăng ký không được để trống");
        if (!StringUtils.hasText(request.fullName()) || !StringUtils.hasText(request.email())
                || !StringUtils.hasText(request.phone()) || !StringUtils.hasText(request.nationalId())) {
            throw new IllegalArgumentException("Họ tên, email, số điện thoại và CCCD không được để trống");
        }

        String email = request.email().trim().toLowerCase(Locale.ROOT);
        String nationalId = request.nationalId().trim();
        if (repository.existsByEmail(email)) {
            throw new StudentAlreadyExistsException("Email đã được đăng ký");
        }
        if (repository.existsByNationalId(nationalId)) {
            throw new StudentAlreadyExistsException("CCCD đã được đăng ký");
        }

        StudentEntity student = new StudentEntity(request.fullName().trim(), email,
                request.phone().trim(), nationalId, trimToNull(request.university()), trimToNull(request.major()));
        return StudentResponseDTO.from(repository.save(student));
    }

    private String trimToNull(String value) {
        return StringUtils.hasText(value) ? value.trim() : null;
    }

    @Override
    @Transactional(readOnly = true)
    public StudentResponseDTO getById(UUID id) {
        if (id == null) throw new StudentNotFoundException("Không tìm thấy hồ sơ sinh viên");
        return repository.findById(id).map(StudentResponseDTO::from)
                .orElseThrow(() -> new StudentNotFoundException("Không tìm thấy hồ sơ sinh viên"));
    }
}
