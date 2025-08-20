# ⚡ Spring Boot 핵심

## 1. Containerless 아키텍처
- 기존에는 **WAS(Web Application Server, 예: Tomcat)**에 배포해야 했음 (`war` 파일)  
- Spring Boot는 **내장 WAS** 제공 → `java -jar app.jar` 실행만으로 애플리케이션 구동 가능  
- 운영 환경에서 **배포 단순화** → Docker 같은 컨테이너 기술과 잘 어울림  

---

## 2. Spring Container
- **Spring Framework의 핵심** → Bean 객체를 관리하는 **컨테이너(Container)**  
- 주요 역할:  
  - 객체 생성 (Bean 등록)  
  - 객체 생명주기 관리  
  - 의존성 주입(DI) 처리  
- 대표적인 구현체: `ApplicationContext`  

---

## 3. IoC와 DI 컨테이너
### IoC (Inversion of Control)
- 객체 생성 및 제어권을 개발자가 아닌 **컨테이너**가 가짐  

### DI (Dependency Injection)
- **의존성 주입**: 직접 의존성을 생성하지 않고 외부에서 주입받음  

```java
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
```

### Assembler
- DI를 통해 필요한 객체를 연결(조립)해주는 역할  
- Spring Container(ApplicationContext)가 곧 Assembler  

---

## 4. DispatcherServlet 동작 원리
- 스프링 MVC의 핵심 Front Controller  
- 모든 요청을 **가장 먼저 받아서** 적절한 Controller로 전달  

동작 흐름:  
1. 클라이언트 요청 → DispatcherServlet 수신  
2. HandlerMapping → 어떤 Controller 호출할지 결정  
3. Controller 실행 → Model, View 반환  
4. ViewResolver → View 결정  
5. 최종 응답 반환  

---

## 5. 컴포넌트 스캔 (Component Scan)
- `@ComponentScan`을 사용하면 특정 패키지 내 `@Component` 계열 애노테이션이 붙은 클래스를 자동 Bean 등록  
- 포함되는 애노테이션:  
  - `@Component`  
  - `@Service`  
  - `@Repository`  
  - `@Controller`, `@RestController`  

- `@SpringBootApplication` 안에는 `@ComponentScan`이 포함

```java
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```
---

## 6. @Controller vs @RestController
### @Controller
- 주로 **View(HTML, JSP 등)** 반환 시 사용  
- `return "home";` → ViewResolver 통해 `home.html` 찾아서 렌더링  
- `@ResponseBody` 붙여야 JSON 데이터 반환 가능  

### @RestController
- `@Controller + @ResponseBody` 결합 애노테이션  
- View 대신 **데이터(JSON, XML)** 직접 반환  
- REST API 개발 시 주로 사용  

---