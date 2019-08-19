package cn.andyoung.service.impl;

import cn.andyoung.service.IAccountService;
import org.springframework.stereotype.Service;

@Service("accountService")
public class AccountServiceImpl implements IAccountService {

  public void saveAccount() {
    System.out.println("执行了保存");
  }

  public void updateAccount(int i) {
    System.out.println("执行了更新" + i);
  }

  public int deleteAccount() {
    System.out.println("执行了删除");
    return 0;
  }
}
