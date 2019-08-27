package cn.andyoung.controller;

import cn.andyoung.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ResponseBody
public class CacheController {
  @Autowired RedisTemplate redisTemplate;

  @RequestMapping("/kv")
  public void kV() {
    ValueOperations ops = redisTemplate.opsForValue();
    ops.set("key", "value");
    Object key = ops.get("key");
    System.out.println(key);
    System.out.println(1111111);
    return;
  }

  @RequestMapping("/hash")
  public void hash() {
    HashOperations ops = redisTemplate.opsForHash();
    ops.put("hash", "hash_key", new User());
    Object o = ops.get("hash", "hash_key");
    System.out.println(o);
    System.out.println(1111111);
    return;
  }

  @RequestMapping("/cache")
  @Cacheable(value = "redisTemplate233")
  public String cache() {

    return new User().getUser(1);
  }
}
