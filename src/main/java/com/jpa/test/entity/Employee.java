package com.jpa.test.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@ToString(exclude = {"department"}) // 순환참조 문제로 JPA에서 연관관계가 있는 엔터티만 매핑시에는 기본 @ToString을 쓰면 안된다.
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "employee_tbl")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_id")
    private Long id;

    @Column(name = "emp_name", nullable = false)
    private String name;

    // fetch는 조인시 속성을 나타낸다
    // EAGER : 무조건 모든 데이터를 로딩해옴(조인을 하건 안하건 로딩해오기 때문에 서비스 부하가 큰 편) 즉시조인
    // LAZY : 조인 구문이 호출되는 경우만 추가로 로딩해줌(실무에서 주로 사용함) 지연조인
    @ManyToOne(fetch = FetchType.LAZY) // 참조당하는 쪽 :one , 참조하는 쪽 : many
    @JoinColumn(name = "dept_id") // 조인 기준컬럼명을 적으면 된다.
    private Department department; // 부서
}
