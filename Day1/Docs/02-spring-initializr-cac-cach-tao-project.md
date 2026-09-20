# Spring Initializr là gì, có mấy cách khởi tạo project

Spring Initializr là công cụ để sinh ra bộ khung (skeleton) của một project Spring Boot: cấu trúc
thư mục chuẩn, file build (`pom.xml`/`build.gradle`), và các thư viện (dependency) mình chọn sẵn.
Có 4 cách dùng nó, đều cho ra cùng một kết quả, chỉ khác nơi thao tác:

**1. Qua trang web start.spring.io**
Vào thẳng https://start.spring.io trên trình duyệt, chọn ngôn ngữ, loại project, version Spring
Boot, các dependency cần, bấm "Generate" để tải về file `.zip`, giải nén ra là có project. Đơn
giản nhất, không cần cài thêm gì.

**2. Tích hợp sẵn trong IDE**
- **IntelliJ IDEA** (bản Ultimate): menu New Project có sẵn mục Spring Initializr.
- **Spring Tool Suite (STS)** / Eclipse với plugin Spring Tools: có wizard "Spring Starter
  Project".
- **VS Code**: cài extension "Spring Initializr Java Support" (thường đi kèm bộ "Spring Boot
  Extension Pack"), dùng Command Palette để tạo project ngay trong VS Code. **Đây là cách mình
  dùng cho project này.**

**3. Dòng lệnh, dùng Spring Boot CLI**
Nếu cài Spring Boot CLI, có lệnh `spring init` để tạo project trực tiếp từ terminal.

**4. Gọi thẳng REST API của start.spring.io**
`start.spring.io` thực chất là một API, có thể gọi bằng `curl`/`httpie`, ví dụ:
```
curl https://start.spring.io/starter.zip -d dependencies=web,jpa,validation -d javaVersion=21 -o shipment-service.zip
```
Ít dùng khi mới học, chủ yếu để tự động hoá (script, CI).

## Cách tạo project trong VS Code (đã làm cho project này)

1. Mở Command Palette: `Cmd + Shift + P`.
2. Gõ và chọn `Spring Initializr: Create a Maven Project...`.
3. VS Code hỏi lần lượt: Spring Boot version → Language (Java) → Group Id → Artifact Id.
4. Tiếp theo chọn dependencies (xem chi tiết ở [chon-dependencies-khi-khoi-tao.md](04-chon-dependencies-khi-khoi-tao.md)).
5. Cuối cùng nó hỏi **"Select destination folder"** — cần chọn đúng thư mục `Day1/Code` để project
   nằm đúng cấu trúc của lộ trình học này.
