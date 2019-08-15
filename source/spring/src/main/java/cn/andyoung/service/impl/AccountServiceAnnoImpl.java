package cn.andyoung.service.impl;

import cn.andyoung.dao.IAccountDao;
import cn.andyoung.service.IAccountService;
import org.springframework.stereotype.Component;

// @Service
@Component("accountServiceAnno")
public class AccountServiceAnnoImpl implements IAccountService {
  private IAccountDao accountDao;

  public void setAccountDao(IAccountDao accountDao) {
    this.accountDao = accountDao;
  }

  public void saveAccount() {

    System.out.println("基于注解的 IOC 配置");
  }
}
