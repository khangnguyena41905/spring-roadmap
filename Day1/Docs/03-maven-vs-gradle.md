# Maven vs Gradle

Cả hai đều là **build tool** cho project Java — nhiệm vụ chung: tải các thư viện (dependency) mà
project cần, biên dịch code, chạy test, đóng gói thành file chạy được (`.jar`).

| | **Maven** | **Gradle** |
| --- | --- | --- |
| File cấu hình | `pom.xml` (XML) | `build.gradle` (Groovy) hoặc `build.gradle.kts` (Kotlin) |
| Cách khai báo | Khai báo thuần (chỉ liệt kê "cần gì"), theo khuôn mẫu cố định | Giống viết một đoạn script — linh hoạt hơn, có thể tự viết logic tùy biến |
| Độ dài | Dài dòng nhưng rất rõ ràng, dễ đoán | Ngắn gọn hơn nhiều |
| Tốc độ build | Chậm hơn với project lớn | Nhanh hơn nhờ cache và build tăng dần (incremental) |
| Độ khó khi mới học | Dễ đọc hiểu hơn vì cấu trúc cố định | Cú pháp "code" nên đường cong học hơi dốc hơn |

Ví dụ khai báo 1 dependency, để hình dung sự khác biệt:

Maven (`pom.xml`):
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

Gradle (`build.gradle`):
```groovy
implementation 'org.springframework.boot:spring-boot-starter-web'
```

**Đối chiếu .NET:** `pom.xml` khá giống tinh thần file `.csproj` — cũng là XML, khai báo
package/dependency theo khuôn mẫu cố định. Gradle giống việc viết một script build tùy biến hơn.

Cả hai dùng chung kho thư viện (Maven Central), Spring Boot hỗ trợ tốt cả hai — không cái nào
"đúng" hơn cái nào.

**Quyết định cho lộ trình này: dùng Maven** — cấu trúc cố định, dễ đọc, phù hợp giai đoạn đang tập
trung học Spring chứ chưa cần tối ưu tốc độ build.
