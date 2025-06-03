package com.onework.boot.server;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

/**
 * OneWork Server Application Test
 *
 * @author onework
 */
@SpringBootTest(classes = OneWorkServerApplication.class)
public class OneWorkServerApplicationTest {

  

    @Test
    public void contextLoads() {

    }

    @Test
    public void main() {
        // 测试应用启动
        OneWorkServerApplication.main(new String[]{});
    }
} 