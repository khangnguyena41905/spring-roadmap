# Cấu trúc project Spring Boot vừa tạo

Project nằm tại `Day1/Code/how-to-init-spring`, tạo bằng Spring Initializr trong VS Code với
group id `spring_beginer`, artifact id `how-to-init-spring`.

Các thành phần chính sau khi tạo:
- `pom.xml` — file cấu hình Maven (build tool, xem [maven-vs-gradle.md](03-maven-vs-gradle.md)), khai
  báo các dependency đã chọn.
- `src/main/java/...` — chứa class chính `HowToInitSpringApplication.java` (điểm khởi động ứng
  dụng, có method `main`).
- `src/main/resources/` — chứa file cấu hình, xem
  [file-cau-hinh-application.md](06-file-cau-hinh-application.md).
- `src/test/` — chứa test.
- `mvnw`, `mvnw.cmd` — Maven Wrapper, cho phép chạy lệnh Maven mà không cần cài Maven riêng trên
  máy.

## Lưu ý: project dùng Spring Boot 4.1.1

Đây là bản khá mới, nên tên vài starter khác với tài liệu/tutorial cũ hay gặp trên mạng:
- `spring-boot-starter-webmvc` thay vì `spring-boot-starter-web` quen thuộc.
- Có **3 starter test riêng theo từng tính năng** (`spring-boot-starter-data-jpa-test`,
  `spring-boot-starter-validation-test`, `spring-boot-starter-webmvc-test`) thay vì gộp chung một
  `spring-boot-starter-test` như các bản cũ.

Không phải lỗi, chỉ là quy ước đặt tên mới hơn của Spring Boot 4 — cần lưu ý khi tra tài liệu để
không nhầm tưởng project cấu hình sai.
