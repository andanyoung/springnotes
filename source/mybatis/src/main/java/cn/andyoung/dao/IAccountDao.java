package cn.andyoung.dao;

import cn.andyoung.domain.Account;

import java.util.List;

public interface IAccountDao {

  /**
   * 查询所有账户，同时获取账户的所属用户名称以及它的地址信息
   *
   * @return
   */
  List<Account> findAllAcc();

  /**
   * 查询所有账户，同时获取账户的所属用户名称以及它的地址信息
   *
   * @return
   */
  List<Account> findAllLazy();
}
