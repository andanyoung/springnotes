package cn.andyoung.druid4mybatis.dao;

import cn.andyoung.druid4mybatis.Druid4mybatisApplication;
import cn.andyoung.druid4mybatis.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Druid4mybatisApplication.class)
public class IUserDaoTest {
  @Autowired private IUserDao userDao;

  @Test
  public void findAllTest() {

    List<User> all = userDao.findAll();
    System.out.println(all);
  }
}
