# ⚡ JPA 연관관계 매핑

## 1. 연관관계 매핑이란?
- 객체는 **참조(Reference)** 를 통해 다른 객체와 관계 맺음  
- DB는 **외래 키(Foreign Key)** 로 관계 맺음  
- JPA의 연관관계 매핑은 이 **객체의 참조**와 **DB의 외래 키**를 매핑하는 작업임  

---

## 2. 연관관계 종류
### 2.1 방향성
- **단방향**: 한쪽에서만 참조 (예: `Member → Team`)  
- **양방향**: 양쪽에서 서로 참조 (`Member ↔ Team`)  

➡️ DB는 항상 **양방향 조회 가능** (FK 기준으로 JOIN), 하지만 객체는 단방향만 가능 → 필요 시 양방향 설정  

### 2.2 다중성
- **1:1 (OneToOne)**  
- **1:N (OneToMany)**  
- **N:1 (ManyToOne)**  
- **N:M (ManyToMany)** → 실무에서는 권장 안 함 (중간 매핑 테이블 직접 설계 권장)  

---

## 3. 연관관계 매핑 어노테이션
- `@ManyToOne` : 다대일 관계  
- `@OneToMany` : 일대다 관계  
- `@OneToOne` : 일대일 관계  
- `@ManyToMany` : 다대다 관계  
- `@JoinColumn(name = "FK명")` : FK 매핑  

---

## 4. 예시: 다대일(N:1) 단방향 매핑
```java
@Entity
public class Member {
    @Id @GeneratedValue
    private Long id;

    private String username;

    @ManyToOne
    @JoinColumn(name = "team_id") // FK
    private Team team;
}

@Entity
public class Team {
    @Id @GeneratedValue
    private Long id;

    private String name;
}
```
- `Member` → `Team` 단방향 매핑  
- `Member.team` 필드가 `team_id` FK를 매핑  

---

## 5. 양방향 매핑
```java
@Entity
public class Team {
    @Id @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<>();
}
```

- `mappedBy` : 반대쪽 매핑의 주인을 지정 (`Member.team`)  
- **연관관계의 주인**만이 FK를 관리 (INSERT, UPDATE)  
- 주인이 아닌 쪽은 조회만 가능  

---

## 6. 연관관계의 주인
- FK를 가지고 있는 쪽이 항상 연관관계의 주인  
- `mappedBy` 속성을 사용하지 않은 쪽이 주인  
- 주인만이 FK 값을 변경할 수 있음  

👉 헷갈리는 포인트: **JPA 양방향 매핑은 사실 단방향 매핑 2개**  
한쪽은 주인, 다른 한쪽은 단순 조회용 거울 역할.  

---

## 7. 일대일(1:1) 관계
- 주 테이블이나 대상 테이블 둘 중 하나에 FK 둠  
- `@OneToOne` + `@JoinColumn` 사용  
- FK를 어디에 두느냐에 따라 다름  
  - **주 테이블 FK**: 객체지향적으로 자연스러움  
  - **대상 테이블 FK**: DB 관리상 유리할 수 있음  
- FK **Unique** 제약조건 추가

---

## 8. 다대다(N:M) 관계
```java
@Entity
public class Student {
    @Id @GeneratedValue
    private Long id;

    @ManyToMany
    @JoinTable(name = "student_course",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id"))
    private List<Course> courses = new ArrayList<>();
}
```
- 중간 테이블(`student_course`) 자동 생성  
- 하지만 실무에서는 **중간 테이블에 추가 컬럼 필요**한 경우 많음 → 다대다 대신 **일대다 + 다대일**로 풀어서 설계하는 게 일반적임  

---
