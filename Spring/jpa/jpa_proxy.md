# ⚡ JPA 프록시(Proxy)

## 1. 프록시(Proxy)
- 프록시는 **실제 엔티티 객체를 대신해서 존재하는 가짜 객체**임  
- JPA에서 `EntityManager.getReference()` 메서드를 호출하면 프록시 객체 반환  
- 실제 엔티티가 필요할 때까지 DB 조회를 미루고, 사용 시점에 쿼리를 실행 (지연 로딩에 활용)  

예시:
```java
Member member = em.getReference(Member.class, 1L); // DB 조회 안 함
System.out.println(member.getClass()); // class com.example.Member$HibernateProxy
```

---

## 2. 프록시의 특징
- **실제 객체를 상속받은 객체** → 타입 체크 시 주의 필요  
- 실제 객체와 다르게 동작하지 않고, 호출 시 내부적으로 **실제 엔티티를 조회해서 위임**  
- **지연 로딩(Lazy Loading)** 구현의 핵심  

---

## 3. 프록시 객체의 초기화
- 프록시 객체의 메서드를 처음 호출하는 순간, 영속성 컨텍스트가 실제 엔티티를 DB에서 조회  
- 조회한 엔티티를 프록시 내부에 저장하고, 이후에는 실제 엔티티에 위임  

```java
Member member = em.getReference(Member.class, 1L); 
System.out.println(member.getName()); // 이 시점에서 DB 조회 발생
```

프록시 초기화 과정:
1. 프록시 객체 메서드 호출  
2. JPA 내부에서 영속성 컨텍스트 확인  
3. 실제 엔티티 조회 후 프록시 내부에 주입  
4. 이후부터는 실제 엔티티가 동작  

---

## 4. 프록시 확인
프록시 객체 여부 및 초기화 여부 확인 방법  

```java
Member member = em.getReference(Member.class, 1L);

// 1. 클래스 타입 확인
System.out.println(member.getClass()); 
// 출력: class com.example.Member$HibernateProxy

// 2. 프록시 초기화 여부 확인
boolean isLoaded = emf.getPersistenceUnitUtil().isLoaded(member);
System.out.println("초기화 여부: " + isLoaded);

// 3. Hibernate 유틸 사용 (Hibernate 전용)
Hibernate.initialize(member);
```

---

## 5. 즉시 로딩(Eager) vs 지연 로딩(Lazy)

### 5.1 즉시 로딩(Eager Loading)
- 엔티티를 조회할 때 연관된 엔티티도 함께 즉시 로딩
- 한번 조회시 여러번의 쿼리가 발생할 수 있음 (N+1 발생)
- `@ManyToOne(fetch = FetchType.EAGER)` 기본값
- 장점: 조회 시 연관 객체를 바로 사용할 수 있음
- 단점: 불필요한 SQL Join 발생 → 성능 저하 가능

### 5.2 지연 로딩(Lazy Loading)
- 실제 연관 객체를 사용할 때 쿼리 실행 이때 
- 연관된 객체는 프록시로 활용되기 때문에 사용시까지 쿼리 발생 안함(N+1 방지)
- `@OneToMany(fetch = FetchType.LAZY)` 기본값
- 장점: 필요한 시점에만 로딩 → 성능 최적화
- 단점: 프록시 초기화 타이밍을 신경써야 함 (영속성 컨텍스트가 닫히면 LazyInitializationException 발생)

---

## 6. 영속성 전이(CASCADE)와 고아 객체

### 6.1 영속성 전이 (Cascade)
- 특정 엔티티를 영속 상태로 만들 때, 연관된 엔티티도 함께 영속/삭제 되도록 전파하는 기능
- 옵션: `PERSIST`, `REMOVE`, `ALL` 등
- 예시: 부모 엔티티를 저장할 때 자식 엔티티도 함께 저장

```java
@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
private List<Child> children = new ArrayList<>();
```

### 6.2 고아 객체 제거 (Orphan Removal)
- 부모 엔티티와의 연관관계가 끊어진 자식 엔티티를 자동으로 삭제하는 기능
- `orphanRemoval = true`
- 예시: 컬렉션에서 child 객체를 제거하면 DB에서도 DELETE 실행

```java
@OneToMany(mappedBy = "parent", orphanRemoval = true)
private List<Child> children = new ArrayList<>();
```

👉 주의: CascadeType.ALL + orphanRemoval = true 조합은 **생명주기를 전적으로 부모 엔티티가 관리**하게 됨.
