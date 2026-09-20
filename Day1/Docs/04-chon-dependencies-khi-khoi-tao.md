# Chọn dependencies khi khởi tạo project

## Đã chọn cho project `how-to-init-spring`

1. **Spring Web** (thực tế Spring Boot 4 gọi là `spring-boot-starter-webmvc`) — nền tảng để viết
   REST API (`@RestController`). Đây là dependency cốt lõi, không phải "giao diện" và không thể bỏ.
2. **Spring Data JPA** — để thao tác database bằng Java object thay vì viết SQL tay.
3. **Validation** — để dùng `@NotNull`, `@Size`... validate dữ liệu đầu vào.
4. **MS SQL Server Driver** (`mssql-jdbc`) — vì chọn SQL Server làm database cho lộ trình này.

## Swagger UI (springdoc-openapi) — không có sẵn trong danh sách

`springdoc-openapi` là thư viện bên thứ ba, **không phải starter chính thức** của Spring nên
không xuất hiện trong danh sách chọn của Spring Initializr. Cần thêm tay vào `pom.xml` sau khi
project đã tạo xong, ví dụ:

```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.6.0</version>
</dependency>
```

Lưu ý: project dùng Spring Boot 4 (khá mới), nên version `springdoc-openapi` này có thể cần điều
chỉnh nếu gặp lỗi tương thích lúc build.

## Làm rõ nhầm lẫn: "giao diện web" nghĩa là gì

Ban đầu có nhầm lẫn giữa hai khái niệm:
- **Frontend/giao diện web riêng** (ví dụ một app React/Angular) — lộ trình này **không cần**,
  chỉ tập trung học backend.
- **Swagger UI** — không phải một ứng dụng frontend riêng, mà là giao diện **tự sinh ra ngay từ
  code backend** để bấm test API. Cái này **vẫn cần** và giữ nguyên trong kế hoạch.

## Về việc test API không dùng Swagger

Nếu không dùng Swagger, README có gợi ý 2 cách thay thế: file `.http` trong VS Code (extension
"REST Client") hoặc Postman. Hiện tại project này vẫn đang đi theo hướng dùng Swagger UI làm giao
diện test chính, theo đúng tiêu chí "xong khi" ban đầu của Day1.
