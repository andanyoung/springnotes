package cn.andyoung.mapper;

import cn.andyoung.MySpringBootApplication;
import cn.andyoung.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MySpringBootApplication.class)
public class UserMapperTest {
  @Autowired private UserMapper userMapper;

  @Test
  public void test() {

    List<User> users = userMapper.queryUserList();
    System.out.println(users);
  }
}
