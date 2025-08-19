# ⚡ 영속성 컨텍스트와 엔티티 관리

## 1. 영속성 컨텍스트 (Persistence Context)
- **엔티티를 영구 저장하는 환경**을 의미함  
- 애플리케이션과 데이터베이스 사이에서 엔티티를 **1차 캐시** 형태로 보관함  
- JPA는 엔티티를 직접 DB에 저장하지 않고, 영속성 컨텍스트에 저장한 뒤 트랜잭션이 커밋되는 시점에 DB에 반영함  

👉 **"엔티티를 관리하는 가상의 저장소"**

---

## 2. 엔티티 매니저 (Entity Manager)
- 엔티티의 생명주기를 관리하는 역할 
- `EntityManager`는 엔티티를 저장, 수정, 삭제, 조회하는 모든 기능을 제공함  

```java
// 엔티티 매니저 사용 예시
EntityManagerFactory emf = Persistence.createEntityManagerFactory("ian")
EntityManager em = emf.createEntityManager();

EntityTransaction tx = em.getTransaction()
tx.begin();

Member member = new Member();
member.setName("이안");

// 영속성 컨텍스트에 저장 (DB X)
em.persist(member);

tx.commit(); // 커밋 시점에 DB 반영
```

---

## 3. 엔티티의 생명주기

엔티티는 4가지 상태를 가짐.  

1. **비영속 (new/transient)**  
   - 영속성 컨텍스트와 전혀 관계없는 상태  
   ```java
   Member member = new Member(); // 비영속 상태
   member.setName("이안");
   ```

2. **영속 (managed)**  
   - 영속성 컨텍스트에 관리되는 상태  
   - `em.persist()` 호출 시 진입  
   ```java
   em.persist(member); // 영속 상태 진입
   ```

3. **준영속 (detached)**  
   - 영속성 컨텍스트에서 분리된 상태  
   - 관리되지 않음, 변경 감지도 동작 안 함  
   ```java
   em.detach(member); // 준영속 상태
   ```

4. **삭제 (removed)**  
   - 엔티티가 삭제된 상태  
   - 트랜잭션 커밋 시 실제 DB에서도 삭제됨  
   ```java
   em.remove(member); // 삭제 상태
   ```

---

## 4. 영속성 컨텍스트의 이점

1. **1차 캐시**  
   - 같은 엔티티를 조회하면 DB에서 다시 가져오지 않고 캐시에서 반환  
   - 동일성(`==`) 보장  
   ```java
   Member m1 = em.find(Member.class, 1L);
   Member m2 = em.find(Member.class, 1L);
   System.out.println(m1 == m2); // true
   ```

2. **쓰기 지연 (Write Behind)**  
   - 트랜잭션 커밋 전까지 SQL을 모아두었다가 한 번에 실행  
   - INSERT/UPDATE 성능 최적화  

3. **변경 감지 (Dirty Checking)**  
   - 엔티티 값만 바꿔도 트랜잭션 커밋 시점에 자동 UPDATE 실행  
   ```java
   Member member = em.find(Member.class, 1L);
   member.setName("이안2"); // UPDATE SQL 자동 실행
   ```

4. **지연 로딩 지원**  
   - 연관된 엔티티를 실제 사용할 때 SELECT 실행 (프록시 활용)  

---

## 5. 플러시 (Flush)

- 영속성 컨텍스트의 변경 내용을 **DB에 반영하는 작업**  
- 플러시가 발생하는 경우
  1. `em.flush()` 직접 호출
  2. 트랜잭션 커밋 시 자동 호출
  3. JPQL 실행 시 자동 호출 (쿼리 실행 전 DB와 동기화 필요하기 때문)  

---

## 6. 준영속 상태 (Detached)

- 영속성 컨텍스트가 더 이상 관리하지 않는 상태  
- 특징
  - 1차 캐시, 변경 감지, 쓰기 지연 모두 적용 안 됨  
- 만드는 방법
  1. `em.detach(entity)` : 특정 엔티티만 준영속화
  2. `em.clear()` : 영속성 컨텍스트 통째로 초기화
  3. `em.close()` : 영속성 컨텍스트 종료  

```java
Member member = em.find(Member.class, 1L);
em.detach(member); // 준영속 상태
member.setName("변경"); // DB 반영 안 됨
```

---