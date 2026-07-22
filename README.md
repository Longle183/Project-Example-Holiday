# Sales Management System

## Cấu trúc thư mục

```
demo/
├── src/main/java/com/example/demo/
│   ├── config/                    # CORS và cấu hình ứng dụng
│   ├── controller/                # Các REST controller theo nghiệp vụ
│   ├── dto/                       # Request/response DTO
│   ├── entity/                    # Mô hình nghiệp vụ
│   ├── exception/                 # Xử lý lỗi tập trung
│   ├── repository/                # Lớp truy xuất dữ liệu (in-memory)
│   └── service/                   # Xử lý nghiệp vụ
├── src/main/resources/
│   ├── application.properties     # Cấu hình backend
│   └── static/
│       └── index.html             # Frontend SPA
├── src/test/                      # Unit/integration test
├── build.gradle                   # Thư viện và cấu hình build
└── gradlew.bat                    # Lệnh chạy ứng dụng trên Windows
```

## Chạy ứng dụng

1. Cài JDK 21 và thiết lập `JAVA_HOME`.
2. Mở terminal tại thư mục `demo`.
3. Chạy `./gradlew.bat bootRun`.
4. Mở `http://localhost:8080`.

Tài khoản mẫu: `admin@sales.local` / `admin123` và `customer@sales.local` / `customer123`.
