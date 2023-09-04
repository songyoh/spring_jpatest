package com.jpa.test.entity;

import com.jpa.test.util.ItemStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "item")
public class Item {

    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // 상품 코드

    @Column(nullable = false, length = 50)
    private String itemName; // 상품명

    @Column(name = "price", nullable = false)
    private int price; // 가격

    @Column(nullable = false)
    private int itemCount; // 재고량

    @Lob // 대량 문자열 매핑시
    @Column(nullable = false)
    private String itemDesc; // 상품 설명

    @Enumerated(EnumType.STRING) // Enum타입 매칭
    private ItemStatus itemStatus;

    private LocalDateTime regDate;

    private LocalDateTime updateDate;
}
