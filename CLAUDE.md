# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Bối cảnh dự án

Thư mục này hiện **chưa có code**, chỉ có file `README` mô tả lộ trình học 1 tuần để xây một dự án Spring Boot mẫu tên **Shipment Service** (quản lý vận đơn). Lộ trình dùng dự án này làm nơi thực hành tuần tự các chủ đề: chuẩn REST API, Spring Security/JWT, OAuth2 (Keycloak), DDD, CQRS nhẹ (không dùng framework, mỗi command/query là một `record` + `@Service` handler), Flyway migration, Pub/Sub trong-process rồi ra RabbitMQ, outbox pattern/idempotent consumer, Docker/Kubernetes.

Người dùng tự học Java/Spring Boot và đối chiếu với nền tảng .NET đã có sẵn (README có các so sánh như Swashbuckle ↔ springdoc-openapi, `IOptions` ↔ `@ConfigurationProperties`, MassTransit consumer ↔ `@RabbitListener`). Khi dự án thực sự được khởi tạo (có `pom.xml`/`build.gradle`, source code...), file này cần được cập nhật lại với lệnh build/test/run thật và kiến trúc package thật — phần này chưa tồn tại nên chưa thể liệt kê.

## Quy tắc bắt buộc khi làm việc trong repo này

1. **Scope cấu hình `.claude` luôn là project.** Mọi hook, MCP server, skill, subagent... cấu hình cho repo này phải đặt ở scope **project** (trong `.claude/` của repo), không đặt ở scope user/global.
2. **Luôn trả lời bằng tiếng Việt**, với văn phong hướng dẫn (tutorial) dành cho người **chưa từng biết gì về Java** — giải thích khái niệm từ cơ bản, không dùng thuật ngữ mà không giải thích, và so sánh với kiến thức nền người dùng đã biết (ví dụ .NET) khi hữu ích.
3. **Chỉ gợi ý, không code thay người dùng.** Đưa ra hướng dẫn, giải thích, review code, chỉ ra chỗ cần sửa/hướng đi — nhưng để người dùng tự viết code để học. Không tự tạo/sửa file code thay họ trừ khi họ yêu cầu rõ ràng là muốn Claude viết.

## Lộ trình 1 tuần (tóm tắt từ README)

| Ngày | Mục tiêu | Sản phẩm cuối ngày |
| --- | --- | --- |
| 1 | Khung API chuẩn + DB + giao diện test API | CRUD chạy được, có Swagger UI |
| 2 | JWT + Spring Security + Filter/Interceptor | Login, cấp token, bảo vệ endpoint |
| 3 | OAuth2 (Keycloak) + bắt đầu DDD | Resource server + domain model (aggregate `Shipment`) |
| 4 | CQRS nhẹ + Flyway migration + Pub/Sub trong-process | Command/Query handler, domain event |
| 5 | RabbitMQ + tạo vận đơn giờ cao điểm | Luồng tạo vận đơn bất đồng bộ (202 Accepted → queue → consumer) |
| 6 | Chống mất tin (outbox, confirms, DLQ) + test tải | Idempotent consumer, k6/JMeter load test |
| 7 | Docker + Kubernetes | Chạy toàn bộ stack trên K8s local (kind/minikube) |
| Bonus | Tách microservice + CI/CD | shipment-service / notification-service riêng, pipeline build/test/deploy |

Chi tiết đầy đủ từng ngày (kiến thức cần học, việc cần làm, tiêu chí "xong khi") nằm trong file `README`.
