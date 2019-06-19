package com.example.nzliveservice.util;

import org.junit.jupiter.api.Test;
import redis.clients.jedis.*;

import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;

public class Text {
    private static Logger log = Logger.getLogger(Test.class);
    @Test
    public void Text(){
//        String s="sdgshjgd/fgdfghdf/derwr/gfh/dfgrt/sdgsd";
//        String[] t=s.split("/");
//        for (String s1:t){
//            System.out.println(s1);
//        }
    }

    @Test
    public void redis(){
//        JedisPoolConfig config = new JedisPoolConfig();
//        config.setMaxTotal(100);
//        config.setMaxIdle(50);
//        config.setMaxWaitMillis(3000);
//        config.setTestOnBorrow(true);
//        config.setTestOnReturn(true);
//        JedisShardInfo jedisShardInfo=new JedisShardInfo("127.0.0.1");
//        jedisShardInfo.setPassword("wingzz00");
//        List<JedisShardInfo> list = new LinkedList<JedisShardInfo>();
//        list.add(jedisShardInfo);
//        ShardedJedisPool pool=new ShardedJedisPool(config,list);
//        System.out.println("jedisShardInfo.getConnectionTimeout():"+jedisShardInfo.getConnectionTimeout());
//        ShardedJedis jedis=pool.getResource();
////        String cs1=jedis.set("cs2","cscs1");
////        System.out.println("cs:"+cs1);
//
//        System.out.println(jedis.get("cs2"));
    }

    @Test
    public void log4j(){
        log.debug("debug");
        log.info("info");
        log.error("error");
    }
}
