package com.jpa.test.repository;

import com.jpa.test.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    // 쿼리 메소드 : 메서드 이름으로 실행될 쿼리문을 제어하기
    // ItemName을 통해 물건정보를 얻어오는 메서드 생성
    List<Item> findByItemName(String itemName);

    // ItemName or ItemDesc 중 하나만 조건에 맞아도 조회해오기
    List<Item> findByItemNameOrItemDesc(String itemName, String itemDesc);

    // ~~ 보다 작다(<) 조건이나 크다(>) 조건
    List<Item> findByPriceLessThan(Integer price);

    // 정렬까지 붙일때는 뒤에 OrderBy를 추가로 붙인다.
    List<Item> findByPriceLessThanOrderByPriceDesc(Integer price);

    // @Query 어노테이션 사용시 JPQL이라는 문법을 통해 조건이 복잡한 쿼리를 직접 작성해 처리할 수 있다.
    // 와일드카드인 *대신 테이블별명을 쓴다.(아래에서는 Item이라는 테이블명에 i라는 별칭을 지정함)
    // like절에서는 왼쪽 %에 파라미터로 전달받는 itemDesc를 사용하므로, :를 추가로 붙인다.(왼쪽에 붙은 :는 @Param으로 전달받은 요소임을 의미)
    // 조건절의 모든 컬럼명 앞에 반드시 별칭.컬럼명 처럼 처리해야한다.
    @Query("select i from Item i where i.itemDesc like %:itemDesc% order by i.price desc")
    List<Item> findByItemDesc(@Param("itemDesc") String itemDesc);

    // 네이티브 쿼리는 특정 SQL엔진에서만 돌아가는 쿼리문을 집어넣을때 사용합니다.
    // 이 경우 문법이 호환되지 않는 엔진의 DB로 바뀌면 네이티브 쿼리 옵션이 적용된 쿼리문을 추가로 수정이 필요할 수 있다.
    // 특정 엔진에서만 쓸 수 있는 쿼리문을 작성해놓고 nativeQuery옵션을 true로 주면된다.
    @Query(value = "select * from item where i.itemDesc like %:itemDesc% order by i.price desc", nativeQuery = true)
    List<Item> findByItemDescByNative(@Param("itemDesc") String itemDesc);

    // JPQL을 이용해 상품명(itemName)에 내가 적은 키워드가 포함된 경우 재고(itemCount) 역순(desc)으로 정렬하는 구문 작성
    // 테스트 코드에서는 상품명에 "상품"이 포함된 케이스를 역순으로 처리해 작성, 통과하는 테스트까지 확인해보기
    @Query("SELECT i FROM Item i WHERE i.itemName LIKE %:itemName% ORDER BY i.itemCount DESC")
    List<Item> findByItemNameDesc(@Param("itemName") String itemName); // findByItemNameOrderByItemCountDesc 명칭이 너무 길어 변경

}
