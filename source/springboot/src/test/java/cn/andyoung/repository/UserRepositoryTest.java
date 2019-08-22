package cn.andyoung.repository;

import cn.andyoung.MySpringBootApplication;
import cn.andyoung.domain.JPAUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MySpringBootApplication.class)
public class UserRepositoryTest {
  @Autowired private UserRepository userRepository;

  @Test
  public void test() {
    List<JPAUser> users = userRepository.findAll();
    System.out.println(users);
  }
}
