package com.jpa.test.repository;

import com.jpa.test.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    // Join구문의 경우 JPQL의 JOIN FETCH 구문을 사용한다.
    // 조건절을 굳이 적어줄 필요는 없으나 Entity 내부에서 이미 어떤 필드값을 통해 참조키 관계가 성립하는지 정의되었기 때문에
    // 어떤 엔터티에 대해서 조인할지만 적어주면 된다.
    @Query("SELECT DISTINCT d FROM Department d JOIN FETCH d.employees")
    List<Department> findAllIncludeEmployees(); // 각 직무별 전체 인원을 가져오기.
}
