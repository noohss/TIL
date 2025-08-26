# ⚡ 상속관계 매핑

## 1. 상속관계 매핑
- 객체 지향에서는 **상속**을 활용해 부모-자식 클래스를 나눠 설계 가능  
- 하지만 RDB에는 상속 개념이 없음 → 대신 **슈퍼타입-서브타입 관계**를 테이블로 구현해야 함  
- JPA는 객체의 상속 구조를 DB 테이블과 매핑하기 위한 여러 전략을 제공  

---

## 2. 슈퍼타입, 서브타입
- **슈퍼타입(Super Type)**: 부모 클래스 (공통 속성/기능 가짐)  
- **서브타입(Sub Type)**: 자식 클래스 (구체적인 속성과 기능 가짐)  

예시 (객체 상속 구조):
```java
@Entity
@Inheritance(strategy = InheritanceType.JOINED) // 상속 매핑 전략 지정
@DiscriminatorColumn // 구분 컬럼 default - DTYPE
public abstract class Item {
    @Id @GeneratedValue
    private Long id;

    private String name;
    private int price;
}

@Entity
public class Book extends Item {
    private String author;
    private String isbn;
}

@Entity
public class Album extends Item {
    private String artist;
}

@Entity
public class Movie extends Item {
    private String director;
    private String actor;
}
```

---

## 3. 슈퍼타입/서브타입 논리 모델 → 실제 물리 모델 구현 전략

### 3.1 조인 전략 (Joined Strategy)
- 각 자식 클래스마다 테이블 생성 + 부모 테이블과 **조인**해서 데이터 조회  
- 정규화된 구조 → 데이터 중복 없음  
- 조회 시 JOIN 필요 → 성능 저하 가능  

```java
@Inheritance(strategy = InheritanceType.JOINED)
```

---

### 3.2 단일 테이블 전략 (Single Table Strategy)
- 모든 자식 엔티티를 **하나의 테이블**에 저장  
- `@DiscriminatorColumn`으로 타입 구분  
- 조회 성능 좋음 (JOIN 없음)  
- 단점: 테이블이 너무 커지고, null 값 칼럼 많이 생김  

```java
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
```

---

### 3.3 구현 클래스마다 테이블 전략 (Table Per Class Strategy)
- 자식 클래스마다 테이블 생성  
- 부모 테이블 없음 → 자식 테이블 각각에 모든 칼럼 존재  
- 조회 시 UNION 필요 → 성능 저하 심각  
- 일반적으로 사용하지 않음 (이 전략은 그냥 제외하자..)

```java
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
```

---

## 4. @MappedSuperclass
- **엔티티가 아님**  
- 공통 매핑 정보만 제공하는 부모 클래스 → 테이블과 매핑되지 않음  
- 상속받는 자식 클래스가 매핑 정보를 그대로 물려받음  

```java
@MappedSuperclass
public abstract class BaseEntity {
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
```

```java
@Entity
public class Member extends BaseEntity {
    @Id @GeneratedValue
    private Long id;
    private String username;
}
```

👉 `Member` 테이블에는 `createdAt`, `updatedAt` 컬럼이 포함됨.  
👉 `@MappedSuperclass`는 단순히 **속성 상속**만 제공할 뿐, 상속관계 매핑과는 다름.  

---
