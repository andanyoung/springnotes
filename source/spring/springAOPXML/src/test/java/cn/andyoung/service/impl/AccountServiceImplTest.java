package cn.andyoung.service.impl;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

// 指定单元测试环境
@RunWith(SpringJUnit4ClassRunner.class)
// 指定配置文件路径
@ContextConfiguration(locations = {"classpath:bean.xml"})
public class AccountServiceImplTest {

  @Autowired AccountServiceImpl accountService;

  @org.junit.Test
  public void saveAccount() {
    accountService.saveAccount();
  }

  @org.junit.Test
  public void updateAccount() {
    accountService.updateAccount(1);
  }

  @org.junit.Test
  public void deleteAccount() {
    accountService.deleteAccount();
  }
}
