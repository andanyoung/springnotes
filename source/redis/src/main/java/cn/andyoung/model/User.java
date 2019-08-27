package cn.andyoung.model;

import org.springframework.cache.annotation.Cacheable;

public class User {

  public String username = "andy";
  public Integer age = 18;

  @Override
  public String toString() {
    return "User{" + "username='" + username + '\'' + ", age=" + age + '}';
  }

  @Cacheable(value = "redisTemplate23", key = "a")
  public String getUser(int id) {
    System.out.println("cache miss, invoke find by id, id:" + id);
    username = "andy_cache";
    return username;
  }
}
