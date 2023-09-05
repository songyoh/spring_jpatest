package com.jpa.test.repository;

import com.jpa.test.entity.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DepartmentRepositoryTest {
    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Test
    public void testLazyEagerLoadingTest() {
        // 3번 사원을 조회할 예정, 부서정보에 대해서는 알고싶지 않을때
        // given
        Long id = 3L;
        // when
        Employee employee = employeeRepository.findById(id).orElseThrow();
        // then
        System.out.println("employee:" + employee); //employee:Employee(id=3, name=임토비)
        System.out.println("employee.getDepartment():" + employee.getDepartment());//employee.getDepartment():Department(id=2, name=개발부)//@ManyToOne(fetch = FetchType.EAGER)
    }
}