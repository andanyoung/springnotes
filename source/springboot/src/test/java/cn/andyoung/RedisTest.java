package cn.andyoung;

import cn.andyoung.domain.JPAUser;
import cn.andyoung.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MySpringBootApplication.class)
public class RedisTest {
  @Autowired private UserRepository userRepository;

  @Autowired private RedisTemplate<String, String> redisTemplate;

  @Test
  public void test() throws JsonProcessingException {
    // 从redis缓存中获得指定的数据
    String userListData = redisTemplate.boundValueOps("user.findAll").get();
    // 如果redis中没有数据的话
    if (null == userListData) {
      // 查询数据库获得数据
      List<JPAUser> all = userRepository.findAll();
      // 转换成json格式字符串
      ObjectMapper om = new ObjectMapper();
      userListData = om.writeValueAsString(all);
      // 将数据存储到redis中，下次在查询直接从redis中获得数据，不用在查询数据库
      redisTemplate.boundValueOps("usr.findAll").set(userListData);
      System.out.println("===============从数据库获得数据===============");
    } else {
      System.out.println("===============从redis缓存中获得数据===============");
    }
    System.out.println(userListData);
  }
}
