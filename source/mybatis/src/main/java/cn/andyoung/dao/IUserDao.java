package cn.andyoung.dao;

import cn.andyoung.domain.QueryVo;
import cn.andyoung.domain.User;

import java.util.List;

public interface IUserDao {

  /**
   * 查询所有用户
   *
   * @return
   */
  List<User> findAll();

  /**
   * 根据 id 查询
   *
   * @param userId
   * @return
   */
  User findById(Integer userId);

  /**
   * 保存用户
   *
   * @param user
   * @return 影响数据库记录的行数
   */
  int saveUser(User user);

  /**
   * 更新用户
   *
   * @param user
   * @return 影响数据库记录的行数
   */
  int updateUser(User user);

  /**
   * 根据 id 删除用户
   *
   * @param userId
   * @return
   */
  int deleteUser(Integer userId);

  /**
   * 根据名称模糊查询
   *
   * @param username
   * @return
   */
  List<User> findByName(String username);

  /**
   * 查询总记录条数
   *
   * @return
   */
  int findTotal();

  /**
   * 根据 QueryVo 中的条件查询用户
   *
   * @param vo
   * @return
   */
  List<User> findByVo(QueryVo vo);

  /**
   * 使用动态sql 根据用户信息，查询用户列表
   *
   * @param user
   * @return
   */
  List<User> findByUser(User user);

  /**
   * 根据 id 集合查询用户
   *
   * @param vo
   * @return
   */
  List<User> findInIds(QueryVo vo);
}
