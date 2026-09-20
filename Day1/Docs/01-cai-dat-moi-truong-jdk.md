# Cài đặt môi trường: JDK

## Vì sao cần JDK trước khi làm gì khác

- Extension "Extension Pack for Java" trong VS Code (IntelliSense, chạy, debug Java) cần có JDK
  cài sẵn trên máy mới hoạt động được.
- Sau khi tạo project, cần JDK để build (`mvn compile`) và chạy ứng dụng.
- Lộ trình học này yêu cầu **JDK 21**.

## Kiểm tra đã có JDK chưa

```
java -version
```
hoặc
```
java --version
```

## Cài qua Homebrew (macOS)

```
brew install openjdk@21
```

## Lỗi thường gặp trên Mac: "Unable to locate a Java Runtime"

Sau khi cài bằng Homebrew, gõ `java --version` vẫn có thể báo lỗi này. Nguyên nhân: Homebrew cài
`openjdk@21` nhưng **không tự động "gắn"** nó vào chỗ macOS tìm Java (gọi là *keg-only* — Homebrew
cố tình không liên kết tự động để tránh đụng độ với các bản Java khác có thể có sẵn trên máy).
Lệnh `java` lúc đó đang chạy vào "vỏ" mặc định của macOS, cái vỏ đó không tìm thấy Java thật nên
báo lỗi.

## Cách fix (3 bước)

**1. Xem đường dẫn cài thật của nó:**
```
brew --prefix openjdk@21
```
Kết quả dạng `/opt/homebrew/opt/openjdk@21` (chip Apple Silicon) hoặc `/usr/local/opt/openjdk@21`
(chip Intel).

**2. Symlink để hệ thống nhận diện Java** (ví dụ với Apple Silicon, thay đúng đường dẫn ở bước 1):
```
sudo ln -sfn /opt/homebrew/opt/openjdk@21/libexec/openjdk.jdk /Library/Java/JavaVirtualMachines/openjdk-21.jdk
```

**3. Thêm vào PATH để gõ lệnh `java` dùng trực tiếp được** (dùng shell `zsh`):
```
echo 'export PATH="/opt/homebrew/opt/openjdk@21/bin:$PATH"' >> ~/.zshrc
source ~/.zshrc
```

Sau đó kiểm tra lại bằng `java --version` — thấy in ra `openjdk 21...` là xong.
