package cn.andyoung.dao;

import cn.andyoung.domain.Role;

import java.util.List;

public interface IRoleDao {
  /**
   * 查询所有角色
   *
   * @return
   */
  List<Role> findAll();
}
