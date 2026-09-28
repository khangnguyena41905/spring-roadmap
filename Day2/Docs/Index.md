# Day2: JWT, Spring Security, Filter/Interceptor

## Mục tiêu hôm nay

Ngày 1 API đã chạy nhưng ai gọi cũng được. Hôm nay làm cho API biết **ai đang gọi** (authentication
- xác thực danh tính) và **họ có quyền làm gì** (authorization - phân quyền), bằng JWT và Spring
Security.

## Kiến thức cần học

- **Luồng của Spring Security** — 4 khái niệm cốt lõi cần hiểu trước khi viết code:
  - `SecurityFilterChain`: chuỗi các bước kiểm tra bảo mật mà **mọi request** phải đi qua trước
    khi tới được controller.
  - `AuthenticationManager`: nơi xác minh "bạn có đúng là ai bạn nói không" (kiểm tra
    username/password hoặc token).
  - `UserDetailsService`: nơi lấy thông tin user (thường từ database) để so khớp lúc đăng nhập.
  - `SecurityContext`: nơi lưu "ai đang đăng nhập" trong suốt vòng đời một request, để các phần
    code khác (controller, service) đọc lại khi cần biết user hiện tại.
- **JWT (JSON Web Token)**: một chuỗi mã hoá đại diện cho "bạn đã đăng nhập, đây là quyền của
  bạn". Client gửi kèm token này ở các request sau thay vì gửi lại username/password mỗi lần.
  - Cấu trúc token (3 phần, phân cách bằng dấu chấm).
  - `HS256` (ký bằng một khoá bí mật dùng chung) và `RS256` (ký bằng cặp khoá công khai/riêng tư)
    khác nhau ở chỗ ai có thể xác minh token.
  - **Expiry** (thời hạn token) và **refresh token** (token phụ, dùng để xin token chính mới khi
    token chính hết hạn, mà không cần đăng nhập lại).
- **"Middleware" trong Spring có 3 tầng** (khác .NET chỉ có một khái niệm middleware duy nhất):
  - `OncePerRequestFilter`: giống middleware ASP.NET nhất — chặn và xử lý mỗi request trước khi
    vào controller. Dùng để viết `JwtAuthenticationFilter`.
  - `HandlerInterceptor`: chạy quanh controller, biết chính xác đang gọi handler (hàm xử lý) nào.
  - `@Aspect` (AOP - Aspect-Oriented Programming, lập trình hướng khía cạnh): chạy quanh một
    method bất kỳ, dùng cho việc như ghi log, đo thời gian mà không sửa code gốc của method đó.
- **CORS**: quyết định trình duyệt ở domain nào được phép gọi API của mình.
- **CSRF**: cơ chế bảo vệ vốn dành cho web dùng session/cookie. Với REST API xác thực bằng token,
  thường tắt CSRF đi vì không cần thiết.

## Việc cần thực hành (làm trong `Code/`)

- [ ] Endpoint `/auth/login`: nhận username/password, trả về access token + refresh token.
- [ ] Tự viết `JwtAuthenticationFilter` (kiểm tra token có hợp lệ trong mỗi request).
- [ ] Một filter gắn `X-Correlation-Id` vào MDC log (để truy vết một request qua nhiều dòng log).
- [ ] Một interceptor đo thời gian xử lý request.

## Tiêu chí hoàn thành

- Gọi endpoint cần đăng nhập mà không có token → trả về `401`.
- Có token nhưng sai quyền → trả về `403`.

## Tiến độ

- [ ] (cập nhật dần khi học xong từng phần, dùng để lần sau mở lại biết đang ở đâu)

## Chủ đề chi tiết

- (sẽ điền link tới các file trong Docs/ khi tạo trong lúc học)
