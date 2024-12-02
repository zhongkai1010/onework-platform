package com.onework.boot.scrape;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class OneworkScrapeApplicationTests {

    @Test
    void contextLoads() {
        // 创建一个包含整数的List
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        // 获取子列表，这里获取索引从1（包含）到3（不包含）的子列表
        List<Integer> subList = list.subList(1, 3);
        System.out.println("原始列表: " + list);
        System.out.println("子列表: " + subList);
        // 修改子列表中的元素
        subList.set(0, 99);
        System.out.println("修改子列表元素后原始列表: " + list);
        System.out.println("修改子列表元素后子列表: " + subList);
    }
}
