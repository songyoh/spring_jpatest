package com.jpa.test.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString(exclude = {"employees"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "dept_tbl")
public class Department { // 부서

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dept_id")
    private Long id;

    @Column(name = "dept_name", nullable = false)
    private String name;

    // 양방향 매핑에서는 상대방 엔터티의 갱신에 관여할 수 없음
    // 단순히 읽기전용(조회) 로만 사용해야 한다
    // mappedBy에는 상대 엔터티와 조인되는 필드명(상대 엔터티 내부에 적힌 변수명) 을 적어주면 되고
    // mappedBy가 적힌 엔터티가 연관관계의 주인
    @OneToMany(mappedBy = "department") // Employee 테이블의 멤버변수 department에 연결
    @Builder.Default
    private List<Employee> employees = new ArrayList<>(); // 직무코드는 하나지만 해당 직무를 받은 인원은 여럿이므로
}
