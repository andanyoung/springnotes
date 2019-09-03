package cn.andyoung.transactional;

import cn.andyoung.transactional.dao.IAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {
  @Autowired IAccount account;

  @Transactional
  public void transfer() throws RuntimeException {

    account.update(90, 1); // 用户1减10块 用户2加10块
    int i = 1 / 0;
    account.update(110, 2);
  }
}
