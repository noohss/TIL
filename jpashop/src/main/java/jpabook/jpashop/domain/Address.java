package jpabook.jpashop.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable // 어딘가에 내장이 될 수 있다.
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // Embeddable 클래스는 jpa 스펙상 기본 생성자를 protected 로 설정.
@AllArgsConstructor
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

}
