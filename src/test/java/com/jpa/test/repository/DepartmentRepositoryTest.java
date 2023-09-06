package com.jpa.test.repository;

import com.jpa.test.entity.Department;
import com.jpa.test.entity.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Test
    @Transactional//(readOnly = true)삭제 // LAZY loading요소를 사용하는경우 @Transactional이 걸려있어야 조인이 된다.
    public void changeDepartmentTest() {
        // 4번 사원의 부서를 2번 -> 1번으로 변경하기
        // given
        Employee employee = employeeRepository.findById(4L).orElseThrow(() -> new RuntimeException("없는 사원입니다."));
        Department department = departmentRepository.findById(1L).orElseThrow(() -> new RuntimeException("없는 부서입니다."));

        // mappedBy를 설정해 연관관계가 맺어진 경우는 따로 처리할 필요는 없고
        // mappedBy가 설정되지 않은 경우
        // 사원쪽에서만 부서를 변경하면 안 되고
        employee.setDepartment(department);
        // 부서쪽에서도 사원정보를 추가해야 한다.
        //department.getEmployees().add(employee);

        employeeRepository.save(employee); // 수정 박제 .save
        // when
        // 1번 부서를 조회해 모든 사원 보기
        // 부서쪽에 사원정보를 추가 안한 경우 1번 부서에서 4번사원이 잡히지 않음
        Department updateDepartment = departmentRepository.findById(1L).orElseThrow();

        System.out.println(updateDepartment.getEmployees());
        // then
    }

    @Test
    @Transactional
    public void NP1Test() {
        // 단일 테이블 자료를 가져올 때는 findAll로 가져와도 상관없지만 연관테이블 데이터 조인해 가져올 때 n+1문제가 발생한다.
        List<Department> departmentList = departmentRepository.findAll();

        departmentList.forEach(department -> {
            System.out.println(department.getEmployees());
        });
        System.out.println("모든 유저가 조회되었습니다.");
    }

    @Test
    public void NP1FetchJoinTest() {
        // JPQL로 fetch join을 수행하는 경우 n+1문제가 해결된다.
        List<Department> departmentList = departmentRepository.findAllIncludeEmployees();

        departmentList.forEach(department -> {
            System.out.println(department.getEmployees());
        });
        System.out.println("모든 유저가 조회되었습니다.");
    }


}