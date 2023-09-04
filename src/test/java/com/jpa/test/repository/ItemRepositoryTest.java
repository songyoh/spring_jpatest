package com.jpa.test.repository;

import com.jpa.test.entity.Item;
import com.jpa.test.util.ItemStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest // 통합테스트, 빈 컨테이너를 생성하고, 등록된 빈을 모두 등록하기 때문에 테스트에 필요없는 빈들도 모두 생성됨
    // 진짜 테스트에 필요한 빈만 활용해 테스트하고 싶다면, 슬라이스 테스트를 검색해 맞춰서 test돌리면 된다.
public class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @Test
    public void createItemList() { // 테스트용 더미데이터 생성
        for(int i = 1; i <= 10; i++) {
            Item item = new Item();
            item.setItemName("테스트 상품: " + i);
            item.setPrice(10000 * i);
            item.setItemDesc("테스트 상품입니다: " + i);
            item.setItemStatus(ItemStatus.SELL);
            item.setItemCount(10 * i);
            item.setRegDate(LocalDateTime.now());
            item.setUpdateDate(LocalDateTime.now());
            itemRepository.save(item);
        }
    }
}
