# Cách khởi động (chạy) project

Có 3 cách để chạy project Spring Boot, dùng cách nào cũng được:

**1. Trong VS Code**
Mở file `HowToInitSpringApplication.java`, phía trên dòng `public static void main(...)` có nút
**▷ Run** (hiện ra khi rê chuột gần đó). Bấm vào để chạy.

**2. Qua Spring Boot Dashboard**
Nếu đã cài "Spring Boot Extension Pack", có tab "SPRING BOOT DASHBOARD" ở thanh bên trái VS Code,
liệt kê app (`how-to-init-spring`), bấm nút Run cạnh tên app.

**3. Qua terminal, dùng Maven Wrapper** (không cần cài Maven riêng, project tự có sẵn `mvnw`)
```
cd Day1/Code/how-to-init-spring
./mvnw spring-boot:run
```

## Lưu ý quan trọng: chưa cấu hình DB thì sẽ lỗi

Project đã có **Spring Data JPA**, nên Spring sẽ tự đòi kết nối database ngay khi khởi động. Tại
thời điểm ghi chú này, project **chưa cấu hình kết nối SQL Server** (chưa có `application.yml`
khai báo URL/user/password, và cũng chưa có SQL Server nào đang chạy để kết nối tới). Nếu chạy
project ở trạng thái này, sẽ gặp lỗi dạng `Failed to configure a DataSource` — đây là lỗi **đúng
như dự đoán**, không phải cấu hình sai. Việc cấu hình Docker Compose + `application.yml` cho SQL
Server sẽ được làm ở bước tiếp theo.
