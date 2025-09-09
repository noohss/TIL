# ⚡ 컬렉션 프레임워크(Collection Framework)

## 1. Collection Framework
- 데이터 그룹을 저장하는 클래스들을 표준화 한 것

    ### 1.1 Collection Framework의 핵심 인터페이스
    - 각 컬렉션을 다루는데 필요한 기능을 가진 List, Set, Map 3개의 인터페이스로 정의

    ### 1.2 인터페이스간의 상속 계층도
    - Lsit와 Set을 구현한 컬렉션 클래스들은 서로 많은 공통 부분을 다시 뽑아서 Collection 인터페이스를 정의
    - Map은 List, Set과는 전혀 다른 형태로 컬렉션을 다루기 때문에 같은 상속 계층도에 포함되지 못함

---

## 2. List
- 순서 보장, 중복 허용
- 구현 클래스: `ArrayList`, `LinkedList`, Stack, Vector 등

    ### 2.1 ArrayList
    - 내부적으로 배열 기반으로 동작하는 List 구현체
    - 인덱스를 기반으로 빠른 랜덤 접근(random access)이 가능
    - 초기 크기를 지정하지 않으면 기본적으로 10, 용량이 부족하면 확장(확장시 지연 발생)
    - 삽입/삭제 시 데이터의 이동 비용이 발생할 수 있음(특히 중간 삽입/삭제)

    ### 2.2 LinkedList
    - 내부적으로 이중 연결 리스트 기반으로 동작하는 List 구현체
    - 각 노드가 데이터 + 이전 노드(prev) + 다음 노드(next)를 가짐
    - 인덱스를 기반으로 접근할 경우 처음/끝부터 순차 탐색해야 하므로 접근 속도가 느림
    - 삽입/삭제 시 포인터만 바꿔주면 되므로, 중간 삽입/삭제에 유리

---

## 3. Set
- 순서 보장 하지 않음, 중복 허용하지 않음
- 구현 클래스: `HashSet`, TreeSet 등

---

## 4. Map
- key, value 쌍으로 이루어진 데이터 집합, 순서 보장하지 않음, key는 중복 허용하지 않음, value는 중복 허용
- 구현 클래스: `HashMap`, TreeMap. Hashtable, Properties 등

---