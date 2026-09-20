# File cấu hình (application.properties / application.yml)

Tương đương với `appsettings.json` bên .NET, Spring Boot dùng file trong
**`src/main/resources/`**. Spring Initializr đã tự tạo sẵn `application.properties` (hiện đang
rỗng) trong project này.

## Đối chiếu .NET

| .NET | Spring Boot | Ghi chú |
| --- | --- | --- |
| `appsettings.json` | `application.properties` **hoặc** `application.yml` | Cả hai định dạng đều dùng được, chọn 1 loại. `.properties` là `key=value` từng dòng; `.yml` là dạng thụt lề, gọn hơn khi cấu hình lồng nhau. |
| `appsettings.Development.json`, `appsettings.Production.json` | `application-dev.properties`/`.yml`, `application-prod.properties`/`.yml` | Gọi là **profile** — file cấu hình riêng theo môi trường, đè lên file gốc. |
| Biến môi trường `ASPNETCORE_ENVIRONMENT` | `spring.profiles.active` (trong file cấu hình hoặc biến môi trường `SPRING_PROFILES_ACTIVE`) | Quyết định profile nào đang được dùng khi chạy. |
| `IOptions<T>` để bind config vào object | `@ConfigurationProperties` | |

## Quyết định cho project này

Sẽ đổi từ `application.properties` sang **`application.yml`** khi cấu hình kết nối SQL Server,
vì cấu hình DB có nhiều mục lồng nhau (host, port, username, password...) nên viết bằng YAML dễ
đọc hơn properties. (Việc đổi/tạo file này và cấu hình kết nối SQL Server sẽ làm ở bước tiếp theo,
chưa thực hiện trong phiên này.)
