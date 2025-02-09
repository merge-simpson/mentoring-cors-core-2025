# CORS Core Library for 2025 Mentorship

멘토링을 위한 CORS 설정 라이브러리입니다. 멘토링을 위해 간소화하였습니다.

## How Has It Been Tested?

- Kotest
- MockMVC

### Tested List

**준비된 작업**

- 테스트용 Spring Boot Application 클래스를 테스트 경로에 생성했습니다.
- 테스트용 구성 속성 리소스(`application.yml`, `cors.yml`) 파일을 테스트 경로에 생성했습니다.

**테스트 항목**

- CORS Properties 빈이 스캔되는지 확인합니다.
- 허용/비허용 `origin`, `headers`, `method`로 요청하여 응답 상태코드 및 헤더를 확인합니다.
  - ✅ 허용 `origin`, `headers`, `method`에 대해 `OPTIONS` 요청 시 200번대 응답을 받습니다.
  - ✅ 허용 `origin`, `headers`, `method`에 대해 `OPTIONS` 요청 시 응답 헤더에 허용 `origin`, `method` 목록을 포함합니다.
  - ✋ 비허용 `origin`, `headers`, `method`에 대해 `OPTIONS` 요청 시 400번대 응답을 받습니다.
  - ✋ 비허용 `origin`, `headers`, `method`에 대해 `OPTIONS` 요청 시 응답 헤더에 허용 `origin`, `method` 목록이 없습니다.

## 제공하는 기능

- 구성속성 파일에 CORS 속성을 입력하면, 속성 값이 매핑된 `CorsProperties` 빈을 사용할 수 있습니다.
- 관련 빈을 사용하기 위해 추가적인 스캔 코드를 입력하지 않아도 되도록 자동으로 빈 등록을 완료합니다.
- Maven 라이브러리로 배포할 수 있도록 스크립트를 작성했습니다.
- JitPack 배포 시 OpenJDK 21로 빌드되도록 설정을 추가하였습니다.

### 구성 속성 예시 (YAML)

```yaml
demo.webmvc.cors:
  allowed:
    headers: "*"
    methods: "*"
    origins:
      - http://localhost:3000
      - https://localhost:3000
    credentials: true
  exposed:
    headers: "*"
  max-age: 3600
```