package cn.andyoung.mybatis.pagehelper.dao;

import cn.andyoung.mybatis.pagehelper.domain.User;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IUserTest {

  @Autowired public IUser user;

  @Test
  public void pageTest() {
    PageHelper.startPage(0, 10);
    Page<User> pages = user.findUsers();
    List<User> users = pages.getResult();
    for (User user : users) {
      System.out.println(user);
    }

    PageHelper.startPage(1, 10);
    pages = user.findUsers();
    users = pages.getResult();
    for (User user : users) {
      System.out.println(user);
    }
  }
}
