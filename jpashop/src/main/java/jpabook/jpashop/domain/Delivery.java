package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "delivery")
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING) // 꼭 STRING으로 쓰자. (ORDINAL - 숫자(1,2,3), enum 상수가 추가되면 순서가 꼬일 수 있음)
    private DeliveryStatus status; // READY, COMP
}
