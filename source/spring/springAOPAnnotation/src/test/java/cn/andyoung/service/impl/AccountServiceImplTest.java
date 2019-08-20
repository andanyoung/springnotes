package cn.andyoung.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:bean.xml")
public class AccountServiceImplTest {

  @Autowired AccountServiceImpl accountService;
  //
  //  public AccountServiceImpl getAccountService() {
  //    return accountService;
  //  }
  //
  //  public void setAccountService(AccountServiceImpl accountService) {
  //    this.accountService = accountService;
  //  }
  //
  //  @Before
  //  public void setUp() {
  //    ClassPathXmlApplicationContext classPathXmlApplicationContext =
  //        new ClassPathXmlApplicationContext("classpath:bean.xml");
  //    this.accountService =
  //        (AccountServiceImpl) classPathXmlApplicationContext.getBean("accountService");
  //  }

  @Test
  public void saveAccount() {
    accountService.saveAccount();
  }

  @Test
  public void updateAccount() {
    accountService.updateAccount(1);
  }

  @Test
  public void deleteAccount() {
    accountService.deleteAccount();
  }
}
