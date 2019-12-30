package com.fl.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;

@SpringBootTest
class DemoApplicationTests {
    @Test
    void contextLoads() {

    }

    /**
     * redis入门案例
     * */
    @Test
    public void testString(){
        String host="192.168.78.128";
        int port=6379;
        Jedis jedis=new Jedis(host,port);
        jedis.set("111","222");
        System.out.println(jedis.get("111"));
    }
}
