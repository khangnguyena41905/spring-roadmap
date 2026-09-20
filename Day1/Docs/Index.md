# Day1: Khung API chuẩn, database, giao diện test

## Mục tiêu hôm nay

Dựng được một Spring Boot API chạy thật: có CRUD, có kết nối database, và có một giao diện để tự
bấm test API (Swagger UI) mà không cần viết tay Postman collection.

## Kiến thức cần học

- **Cấu trúc package**: chia code theo *tầng* (layer: `controller/`, `service/`, `repository/`)
  hoặc theo *feature* (mỗi tính năng một package con chứa đủ controller/service/repository của
  riêng nó). Người mới nên bắt đầu theo tầng cho dễ hình dung luồng chạy.
- **Spring DI (Dependency Injection)**: thay vì bạn tự `new` một object, Spring tự tạo sẵn các
  object (gọi là *bean*) và "tiêm" (inject) vào nơi cần dùng, thường qua constructor. Bạn chỉ cần
  khai báo class đó là một bean (`@Service`, `@Repository`, `@Component`...), Spring lo phần còn
  lại.
- **`@RestController`**: annotation đánh dấu một class là API controller — mỗi method trong đó xử
  lý một endpoint và tự động trả kết quả về dạng JSON.
- **DTO tách khỏi entity**: *Entity* là object ánh xạ trực tiếp vào một bảng trong database. *DTO*
  (Data Transfer Object) là object riêng dùng để nhận/trả dữ liệu qua API. Tách hai cái ra để
  tránh lộ cấu trúc database ra ngoài, và để API ổn định dù entity thay đổi.
- **`@ControllerAdvice` + `ProblemDetail`**: cách xử lý lỗi tập trung một chỗ, để mọi lỗi trong
  toàn bộ API đều trả về **cùng một định dạng** (chuẩn `ProblemDetail`, theo RFC 7807), thay vì
  mỗi endpoint tự bịa ra một kiểu lỗi khác nhau.
- **Jakarta Validation**: dùng annotation như `@NotNull`, `@Size` gắn trực tiếp lên field của DTO
  để Spring tự kiểm tra dữ liệu đầu vào hợp lệ hay không, không cần viết `if` tay.
- **Phân trang (pagination)**: trả dữ liệu theo từng trang nhỏ thay vì trả hết toàn bộ bảng một
  lần — quan trọng khi dữ liệu lớn.
- **Mã HTTP cần nhớ**: `200` OK, `201` Created (vừa tạo xong), `204` No Content (thành công nhưng
  không có gì trả về, ví dụ sau khi xoá), `400` Bad Request (dữ liệu gửi lên sai), `404` Not Found,
  `409` Conflict (xung đột dữ liệu).
- **Kết nối DB**:
  - `application.yml` theo *profile* (ví dụ `dev`, `prod` dùng cấu hình DB khác nhau).
  - **HikariCP**: connection pool — thay vì mở/đóng kết nối DB mới cho mỗi request (rất chậm), nó
    giữ sẵn một nhóm kết nối để tái sử dụng.
  - **Spring Data JPA**: thư viện giúp thao tác database bằng object Java (gọi `repository.save(...)`)
    thay vì tự viết câu lệnh SQL.
- **Swagger UI (springdoc-openapi)**: một giao diện web được sinh tự động từ code, cho phép bạn
  bấm thử từng API ngay trên trình duyệt mà không cần công cụ ngoài.

## Việc cần thực hành (làm trong `Code/`)

- [ ] Tạo project bằng **Spring Initializr** (Java 21, dependency: Web, JPA, Validation, driver
      Postgres hoặc SQL Server).
- [ ] Viết `docker-compose.yml` để chạy database.
- [ ] Làm CRUD (Create/Read/Update/Delete) cho entity `Shipment`.
- [ ] Mở được Swagger UI ở `/swagger-ui.html`.

## Đối chiếu .NET (nếu bạn từng quen .NET)

- Swashbuckle ↔ springdoc-openapi
- `IOptions` ↔ `@ConfigurationProperties`

## Tiêu chí hoàn thành ngày hôm nay

- Gọi được đầy đủ CRUD qua Swagger UI.
- Khi có lỗi, API luôn trả về đúng một định dạng lỗi thống nhất (`ProblemDetail`).

## Tiến độ

- [x] Dựng project bằng Spring Initializr (`Day1/Code/how-to-init-spring`, Spring Boot 4.1.1, Maven)
- [ ] Chạy được database bằng Docker Compose
- [ ] CRUD `Shipment` hoạt động qua Swagger
- [ ] Xử lý lỗi thống nhất bằng `@ControllerAdvice` + `ProblemDetail`

## Chủ đề chi tiết

1. [Cài đặt môi trường: JDK](01-cai-dat-moi-truong-jdk.md)
2. [Spring Initializr là gì, có mấy cách khởi tạo project](02-spring-initializr-cac-cach-tao-project.md)
3. [Maven vs Gradle](03-maven-vs-gradle.md)
4. [Chọn dependencies khi khởi tạo project](04-chon-dependencies-khi-khoi-tao.md)
5. [Cấu trúc project Spring Boot vừa tạo](05-cau-truc-project-spring-boot.md)
6. [File cấu hình (application.properties / application.yml)](06-file-cau-hinh-application.md)
7. [Cách khởi động (chạy) project](07-cach-khoi-dong-project.md)
