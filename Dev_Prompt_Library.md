# DEV PROMPT LIBRARY — BỘ SỔ TAY PROMPT 5 THÀNH TỐ CHUẨN DOANH NGHIỆP
> **Dự án:** Student OJT Management Service
> **Lập trình viên:** [Họ và Tên Học Viên]
> **Lớp:** 6H Masterclass — AI Agent & Code Audit (R2S Academy)
----
## 1. CẤU HÌNH LÁ CHẮN VÙNG ĐỎ (.copilotignore)
```gitignore
src/main/resources/application*.yml
src/main/resources/application*.properties
.env
*.pem
*.key
secrets/
```
----
## 2. PROMPT 5 THÀNH TỐ (R-C-T-C-O) TẠO REST API & PII MASKING
```text
[ROLE]
Bạn là Senior Java Architect với 10+ năm kinh nghiệm thiết kế REST API Spring Boot Enterprise và tuân thủ
nghiêm ngặt Clean Architecture.
[CONTEXT]
Tôi đang viết API Đăng ký Sinh viên OJT trên Spring Boot 4.0.9, Java 21, Spring Data JPA, H2 Database và Jakarta Validation. Dự án triển khai trong đường dẫn thư mục "D:\WORK\Web\Back_end\AI\demo". Package chính là com.r2s.masterclass.student.
[TASK]
Khởi tạo toàn bộ bộ mã nguồn Java cho phân hệ Student OJT Service gồm 7 file:
1. StudentRequestDTO (Java Record + Jakarta Validation Annotations)
2. StudentResponseDTO (Java Record + Logic Masking PII)
3. StudentEntity (JPA Entity với ID UUID, status mặc định PENDING_APPROVAL)
4. StudentRepository (Spring Data JPA với phương thức existsByEmail và existsByNationalId)
5. StudentService & StudentServiceImpl (Có logic kiểm tra trùng Email/CCCD)
6. StudentController (REST API với response HTTP 201 Created và 200 OK)
7. GlobalExceptionHandler (@RestControllerAdvice bắt lỗi Validation 400, Duplicate 409, Not Found 404)
[CONSTRAINT]
- CẤM TUYỆT ĐỐI trả StudentEntity ra Controller. Controller chỉ giao tiếp qua DTO Records.
- BẮT BUỘC Validate RequestDTO: fullName (@NotBlank), email (@Email), phone (@Pattern 10 số VN),
nationalId (@Pattern 12 số).
- BẮT BUỘC Masking PII trong ResponseDTO: Phone (090*****67), NationalId (079******789).
- BẮT BUỘC kiểm tra trùng Email/CCCD trong Service, ném StudentAlreadyExistsException trả về 409 Conflict. Kiểm tra nullpointer Exception và tránh N + 1
- Ẩn toàn bộ thông tin SQL Stacktrace khi bị lỗi.
[OUTPUT]
Mã nguồn Java sạch sẽ, đầy đủ annotation, có comment giải thích ngắn gọn bằng tiếng Việt, phân chia rõ tên file
và đường dẫn package com.r2s.masterclass.student.
```
----
## 3. NHẬN XÉT AUDIT CỦA LẬP TRÌNH VIÊN (HUMAN-IN-THE-LOOP)
- **Lỗi phát hiện khi tích hợp:** Import `EntityScan` ban đầu dùng package cũ, không tương thích Spring Boot 4. Đã chuyển sang `org.springframework.boot.persistence.autoconfigure.EntityScan`.
- **Lỗi phát hiện qua request mẫu:** Số điện thoại hợp lệ `0901234567` bị trả 400 do regex ban đầu chỉ khớp 9 chữ số. Đã sửa regex để kiểm tra đúng 10 chữ số.
- **Điều chỉnh theo API Contract:** Bổ sung `university` và `major` vào Entity/DTO/service; cập nhật endpoint đăng ký thành `/api/v1/students/register` và response trả `maskedPhone`, `maskedNationalId`.
- **Kiểm tra nghiệp vụ trong mã:** Service kiểm tra email và CCCD trùng lặp; `GlobalExceptionHandler` ánh xạ lỗi trùng sang 409 và không trả SQL/stacktrace trong response.
- **Trạng thái xác minh:** Đã sửa các lỗi thể hiện qua build/request và cập nhật request mẫu. Chưa chạy lại Maven build hoặc toàn bộ kịch bản sau các chỉnh sửa cuối, nên chưa ghi nhận Quality Gate là 100% PASS.
