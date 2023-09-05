package com.jpa.test.repository;

import com.jpa.test.entity.Item;
import com.jpa.test.util.ItemStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest // 통합테스트, 빈 컨테이너를 생성하고, 등록된 빈을 모두 등록하기 때문에 테스트에 필요없는 빈들도 모두 생성됨
    // 진짜 테스트에 필요한 빈만 활용해 테스트하고 싶다면, 슬라이스 테스트를 검색해 맞춰서 test돌리면 된다.
public class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @Test
    //@Transactional
    public void createItemList() { // 테스트용 더미데이터 생성
//        for(int i = 1; i <= 10; i++) {
//            Item item = new Item();
//            item.setItemName("테스트 상품: " + i);
//            item.setPrice(10000 * i);
//            item.setItemDesc("테스트 상품입니다: " + i);
//            item.setItemStatus(ItemStatus.SELL);
//            item.setItemCount(10 * i);
//            item.setRegDate(LocalDateTime.now());
//            item.setUpdateDate(LocalDateTime.now());
//            itemRepository.save(item);
//        }
        for(int i = 1; i <= 10; i++) {
            Item item = new Item();
            item.setPrice(10000 * i);
            if(i % 2 == 0){
                item.setItemName("test item: " + i);
                item.setItemDesc("It's test item: " + i);
            } else {
                item.setItemName("테스트 상품명: " + i);
                item.setItemDesc("테스트 상품입니다: " + i);
            }
            item.setItemStatus(ItemStatus.SELL);
            item.setItemCount(10 * i);
            item.setRegDate(LocalDateTime.now());
            item.setUpdateDate(LocalDateTime.now());
            itemRepository.save(item);
        }
    }

    @Test
    public void findByItemDescTest() {
        // given
        // 검색어로 "상품" 지정
        String searchKeyword = "상품";
        int assertIdx = 0;
        int assertPrice = 90000;
        int assertCount = 90;
        // when
        List<Item> result = itemRepository.findByItemDesc(searchKeyword); // 오름차순 -> 내림차순 정렬
        System.out.println(searchKeyword);
        // then
        assertEquals(result.get(0).getPrice(), assertPrice);
        assertEquals(result.get(0).getItemCount(), assertCount);
    }

    @Test
    public void findByItemNameDescTest() {
        // given
        // 검색어로 "상품" 지정
        String searchKeyword = "상품";
        int assertIdx = 0;
        int assertPrice = 90000;
        int assertCount = 90;
        // when
        List<Item> result = itemRepository.findByItemNameDesc(searchKeyword); // 오름차순 -> 내림차순 정렬
        System.out.println(searchKeyword);
        // then
        assertEquals(result.get(assertIdx).getPrice(), assertPrice);
        assertEquals(result.get(assertIdx).getItemCount(), assertCount);
    }
}
