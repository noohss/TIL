# ⚡ JPA 기본 개념

## 1. JPA - Java Persistence API

### 1.1 JPA란
- `Java` 진영의 **ORM 표준**임  
- `JDBC`와 애플리케이션 사이에서 동작함  
- JPA 자체는 **명세**일 뿐, 실제 동작을 직접 구현하지 않음  
- 즉 JPA = 규약, 실제 구현체는 Hibernate, EclipseLink, DataNucle 등이 있음  
  👉 이 중 **Hibernate**가 가장 널리 쓰임  

---

### 1.2 JPA, Hibernate, Spring Data JPA 관계
- **JPA**: "이렇게 동작해야 한다"라는 **표준 명세** (인터페이스)  
- **Hibernate**: JPA 명세를 구현한 대표적인 ORM 프레임워크  
- **Spring Data JPA**: JPA를 더 편리하게 사용할 수 있도록 **Spring에서 제공하는 모듈**  

👉 정리  
- JPA = 표준  
- Hibernate = 구현체  
- Spring Data JPA = JPA 사용 편의성 강화 라이브러리  

---

### 1.3 ORM
- **Object-Relational Mapping (객체 관계 매핑)**  
- 객체(Object)와 RDBMS의 데이터를 매핑해주는 기술  
- 즉, **자바 객체 ↔ 테이블** 사이의 불일치를 해소해줌  

---

## 2. JPA(ORM)을 사용해야 하는 이유

### 2.1 SQL 중심 개발의 한계
1. **SQL 작성 반복**  
   - 단순 CRUD에도 매번 SQL 작성 필요  
2. **객체-관계 불일치 문제**  
   - 객체 지향 언어(Java) 모델 ↔ DB 테이블 모델 불일치  
   - 상속 구조 → 테이블로 매핑 어려움  
   - 객체 그래프 탐색(`member.getOrders()`)은 자연스럽지만, SQL은 JOIN 직접 작성해야 함  
3. **유지보수성 저하**  
   - 필드 하나 추가 시 SQL, 매퍼, DTO 등 여러 곳 수정해야 함  

---

### 2.2 JPA가 제공하는 해결책
- **SQL 자동 생성**  
  - 단순 CRUD는 SQL 작성 없이 처리 가능  
- **객체지향 개발 가능**  
  - 연관관계 매핑 지원 → 객체 그래프 탐색 가능  
- **변경 감지 (Dirty Checking)**  
  - 엔티티 값 변경만으로 SQL UPDATE 자동 실행  
- **캐싱 효과**  
  - 1차 캐시, 쓰기 지연 등으로 성능 최적화  
- **DB 벤더 독립성**  
  - DB 방언(Dialect) 통해 다양한 DB 지원  

---