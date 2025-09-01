# ⚡ JPA 값 타입(Value Type)

## 1. 기본 값 타입
### 1.1 엔티티 타입 vs 값 타입
- **엔티티 타입**
  - 식별자(@Id)가 존재 → **생명주기 관리 가능**
  - 동일성(identity)로 구분 (`==` 비교)
  - 예: `Member`, `Order`  

- **값 타입**
  - 식별자가 없음 → 생명주기를 엔티티에 의존
  - 동등성(equality)로 구분 (`equals()` 비교)
  - 예: `String`, `Integer`, `Address(VO)`  

---

## 2. 임베디드 값(복합 값 타입)
- 하나의 엔티티 안에 값 객체(VO, Value Object)를 내장해서 사용  
- `@Embeddable` → 값 타입 정의  
- `@Embedded` → 엔티티에서 포함  

예시:
```java
@Embeddable
public class Address {
    private String city;
    private String street;
    private String zipcode;
}

@Entity
public class Member {
    @Id @GeneratedValue
    private Long id;

    @Embedded
    private Address address;
}
```
- 장점: 재사용 가능, 객체 지향적인 설계 가능  

---

## 3. 컬렉션 값 타입
### 3.1 값 타입 컬렉션 사용
- 값 타입을 컬렉션(`List`, `Set`, `Map`)으로 보관 가능  
- `@ElementCollection` + `@CollectionTable` 사용  

예시:
```java
@Entity
public class Member {
    @Id @GeneratedValue
    private Long id;

    @ElementCollection
    @CollectionTable(name = "favorite_foods",
                     joinColumns = @JoinColumn(name = "member_id"))
    private Set<String> favoriteFoods = new HashSet<>();
}
```

### 3.2 값 타입 컬렉션의 제약사항
- 컬렉션은 `LAZY` 로딩이 기본  
- `값 타입은 엔티티와 달리 식별자가 없음`  
- 수정이 어려움 → 컬렉션 변경 시 **기존 데이터를 모두 삭제 후 다시 삽입**  

### 3.3 값 타입 컬렉션의 대안
- 값 타입 컬렉션 대신 **엔티티로 승격**시켜 사용 (식별자 추가)  
- 엔티티로 만들면 변경 감지(Dirty Checking)와 캐싱, 추적이 가능해짐  

---

## 4. 불변 객체
- 값 타입은 **불변(immutable) 객체**로 설계하는 것이 권장됨  
- 값 공유로 인한 부작용 방지 → `setter` 제거  
- 생성자로만 값 설정 가능하게 설계  

예시:
```java
@Embeddable
public class Address {
    private final String city;
    private final String street;
    private final String zipcode;

    protected Address() {} // JPA 기본 생성자

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
```

---

## 5. 값 타입의 비교
- 값 타입은 `동등성(equality)` 비교로 판단 (`equals()` & `hashCode()`)  
- 엔티티는 **동일성(identity)** 비교 (`==`)  
- 예: 같은 주소(city, street, zipcode 동일)라면 동일 값으로 판단  

---