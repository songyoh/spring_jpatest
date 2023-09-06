package com.jpa.test.repository;

import com.jpa.test.entity.Department;
import com.jpa.test.entity.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class EmployeeRepositoryTest {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Test
    void dummyDataInsert() {
        // 부서 2개 생성
        Department d1 = Department.builder()
                .name("총무부")
                .build();
        Department d2 = Department.builder()
                .name("개발부")
                .build();
        // 저장
        departmentRepository.save(d1);
        departmentRepository.save(d2);

        // 직원 생성
        Employee e1 = Employee.builder()
                .name("라이옹")
                .department(d1)
                .build();
        Employee e2 = Employee.builder()
                .name("고구미")
                .department(d1)
                .build();
        Employee e3 = Employee.builder()
                .name("임토비")
                .department(d2)
                .build();
        Employee e4 = Employee.builder()
                .name("뉴진숙")
                .department(d2)
                .build();
        // 저장
        employeeRepository.save(e1);
        employeeRepository.save(e2);
        employeeRepository.save(e3);
        employeeRepository.save(e4);
    }

    @Test
    public void testFindOne() {
        // at com.jpa.test.entity.Department$HibernateProxy$IhaNQkCF.toString(Unknown Source) @ToString 순환참조문제(서로가 서로를 계속 반복해서 참조하는 문제 발생)
        // Employee, Department 엔터티 @Tostring에 (exclude = {"department"}),(exclude = {"employees"}) 설정 후 test코드를 돌리니 통과되었다.
        // given
        Long id = 2L;
        // when
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("없는 사원번호가 조회되었습니다."));
        // then
        System.out.println("employee: " + employee); // employee: Employee(id=2, name=고구미)
        System.out.println("employ's department: " + employee.getDepartment()); //employ's department: Department(id=1, name=총무부) //@ManyToOne(fetch = FetchType.EAGER)
        assertEquals("고구미", employee.getName());
    }

    @Test
    public void testFindDepartment() {
        // given
        Long id = 1L;
        // when
        Department department = departmentRepository.findById(id).orElseThrow(() -> new RuntimeException("없는 부서입니다."));
        // then
        System.out.println("department: " + department); //department: Department(id=1, name=총무부)//@ManyToOne(fetch = FetchType.EAGER)
    }
}