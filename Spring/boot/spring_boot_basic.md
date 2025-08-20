# ⚡ Spring Boot 기본 개념

## 1. Spring Boot란?
- **Spring Framework 기반**의 경량화된 프레임워크  
- **독립 실행형 애플리케이션**을 빠르게 개발할 수 있도록 지원  
- "설정보다 관례(Convention over Configuration)" 철학을 기반으로 복잡한 설정을 최소화  

---

## 2. Spring Boot의 목표
1. **빠른 생산성** → 개발자가 비즈니스 로직에 집중할 수 있도록 지원  
2. **설정 간소화** → 스타터 의존성과 자동 설정 제공  
3. **운영 편의성** → Actuator, Metrics, Health Check 내장  
4. **배포 단순화** → 내장 WAS(Tomcat 등)로 실행 가능  
5. **스프링 생태계와 통합** → JPA, Security, Batch 등과 손쉽게 연동  

---

## 3. 주요 특징

### 3.1 Starter 의존성
- 자주 쓰는 라이브러리를 모아둔 패키지 제공
```gradle
// 예시
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
}
```

---

### 3.2 자동 설정 (Auto Configuration)
- `@SpringBootApplication` 하나로 스프링 앱 실행 가능  
- 필요한 의존성이 있으면 자동으로 Bean 등록 및 환경 구성  

---

### 3.3 내장 서버 (Embedded Server)
- Tomcat, Jetty, Undertow 내장  
- 별도 설치 필요 없이 `main()` 실행만으로 서버 구동  

```bash
$ ./gradlew bootRun
```

## 4. Spring vs Spring Boot 차이

| 구분 | Spring Framework | Spring Boot |
|------|------------------|-------------|
| 설정 | XML/JavaConfig 직접 작성 | 자동 설정 제공 |
| 서버 실행 | 외부 WAS 필요(Tomcat 등) | 내장 WAS 지원 |
| 의존성 관리 | 개별 라이브러리 추가 | Starter 제공 |

---