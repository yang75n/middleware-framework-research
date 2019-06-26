package com.yqw.redis;

import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Redis Java接口测试
 */
public class TestRedis {
    private Jedis jedis;

    public static void main(String[] args) {
        TestRedis redis = new TestRedis();
        redis.init();
        redis.testString();
        redis.testMap();
        redis.testList();
        redis.testSet();
    }

    public void init() {
        jedis = new Jedis("127.0.0.1", 6379);
        jedis.auth("qiwen123");
    }

    public void testString() {
        // 设置值
        jedis.del("string1");
        jedis.set("string1", "qiwen");
        System.out.println(jedis.get("string1"));

        // 字符串拼接
        jedis.append("string1", " is my name"); //拼接
        System.out.println(jedis.get("string1"));

        // 删除字符串
        jedis.del("string1");
        System.out.println(jedis.get("string1"));

        // 设置多个键值对
        jedis.del("name");
        jedis.del("age");
        jedis.mset("name", "qiwen", "age", "28");
        // 加1操作
        jedis.incr("age");
        System.out.println(jedis.get("name") + "-" + jedis.get("age"));
    }

    public void testMap() {
        jedis.del("user");
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", "qiwen");
        map.put("age", "28");
        map.put("sex", "male");
        jedis.hmset("user", map);

        // 参数1 key 参数2 filed 参数3 field
        List<String> rsmap = jedis.hmget("user", "name", "age", "sex");
        System.out.println(rsmap);

        // 删除map中的某个键值  
        jedis.hdel("user", "age");
        System.out.println(jedis.hmget("user", "age"));
        System.out.println(jedis.hlen("user"));
        System.out.println(jedis.exists("user"));
        System.out.println(jedis.hkeys("user"));
        System.out.println(jedis.hvals("user"));

        Iterator<String> iter = jedis.hkeys("user").iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            System.out.println(key + ":" + jedis.hmget("user", key));
        }
    }

    public void testList() {
        // 删除list
        jedis.del("language");
        System.out.println(jedis.lrange("language", 0, -1));

        jedis.lpush("language", "english");
        jedis.lpush("language", "chinese");
        jedis.lpush("language", "japanese");

        // 第一个是key，第二个是起始位置，第三个是结束位置，jedis.llen获取长度 -1表示取得所有  
        System.out.println(jedis.lrange("language", 0, -1));

        jedis.del("language");
        jedis.rpush("language", "java");
        jedis.rpush("language", "c");
        jedis.rpush("language", "python");
        System.out.println(jedis.lrange("language", 0, -1));

        // 双向列表，jedis 排序 
        jedis.del("a");
        jedis.rpush("a", "1");
        jedis.lpush("a", "6");
        jedis.lpush("a", "3");
        jedis.lpush("a", "9");
        System.out.println(jedis.lrange("a", 0, -1));
        System.out.println(jedis.sort("a"));
        System.out.println(jedis.lrange("a", 0, -1));
    }

    public void testSet() {
        jedis.del("b");
        jedis.sadd("b", "张三");
        jedis.sadd("b", "李四");
        jedis.sadd("b", "王五");
        jedis.sadd("b", "胜七");
        jedis.sadd("b", "段九");

        // 移除
        jedis.srem("b", "胜七");
        System.out.println(jedis.smembers("b"));
        System.out.println(jedis.sismember("b", "胜七"));
        System.out.println(jedis.srandmember("b"));
        System.out.println(jedis.scard("b"));
    }
}