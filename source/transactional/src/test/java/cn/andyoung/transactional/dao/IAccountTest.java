package cn.andyoung.transactional.dao;

import cn.andyoung.transactional.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IAccountTest {

  @Autowired AccountService accountService;

  @Test
  public void transfer() {
    accountService.transfer();
  }
}
