
# UNIS :: 2025 이화여대 대동제 럭키드로우 이벤트
![Image](https://github.com/user-attachments/assets/f7edf254-41a1-49ea-a5e3-6c0b25caea97)
> 2025 이화여대 대동제 럭키드로우 이벤트 백엔드 레포지토리입니다.

## 🔗 배포 및 문서 링크
- 🌐 [HTTPS 배포 주소](https://www.2025-unis-fest-back.site/)
- 📘 [API 명세서 (Notion)](https://www.notion.so/API-1d5eff688ebe802dbf9ad03d9e61688a?pvs=4)
- 💻 [프론트엔드 GitHub 레포지토리](https://github.com/2025-UNIS-EWHA-FESTIVAL/2025-UNIS-EWHA-FESTIVAL-FRONT)


## 🦕 Stacks

<!-- prettier-ignore -->
### ⚙️ Tech Stack

| Category       | Technology            | Version | Role / Why We Use It                       |
|----------------|-----------------------|---------|--------------------------------------------|
| **Language**   | Java                  | 21      | Core backend language                      |
| **Framework**  | Spring Boot           | 3.2.x   | REST APIs, DI, configuration               |
| **Build Tool** | Gradle                | 8.x     | Build, dependency management               |
| **Database**   | MySQL (AWS RDS)       | 8.0     | Relational data storage                    |
| **ORM**        | Spring Data JPA       | 6.x     | Data access layer                          |
| **Auth**       | Spring Security       | 6.x     | Authentication & authorization             |
| **Container**  | Docker                | 24.x    | Containerization & local parity            |
| **CI/CD**      | GitHub Actions        | —       | Build, test, deploy pipeline               |
| **Cloud**      | AWS EC2, RDS          | —       | Hosting & managed database                 |
| **Reverse‑Proxy / SSL** | Nginx        | 1.26+   | TLS termination, reverse proxy to Spring Boot (port 8080)	      |


## 🦕 Folder Structure
```
2025-unis-fest-back/
├── Dockerfile # 도커
├── src/main/java/come/exmaple/__unis_fest_back/
├── ├── ├── Application.java
├── ├── ├── dto/
├── ├── ├── repository/
├── ├── ├── config/
├── ├── ├── entity/
├── ├── ├── controller/
├── ├── ├── service/
├── ├── ├── global/ # API response, Global Exception Handler 등
```

## 📘 Commit Convention
``` Markdown
<타입> : <제목>
ex) feat: 로그인 기능 추가

1. feat : 새로운 기능 추가
2. fix : 버그 수정
3. docs : 문서 수정
4. test : 테스트 코드 추가
5. refactor : 코드 리팩토링, 파일 혹은 폴더명을 수정하거나 옮기는 작업만인 경우, 파일을 삭제하는 작업만 수행한 경우
6. chore : 코드 외 빌드 부분 혹은 패키지 매니저 수정사항

타입은 소문자로 고정
```
