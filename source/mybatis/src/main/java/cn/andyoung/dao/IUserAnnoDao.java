package cn.andyoung.dao;

import cn.andyoung.domain.Account;
import cn.andyoung.domain.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

/** 给予注解的dao */
public interface IUserAnnoDao {

  /**
   * 查询所有用户
   *
   * @return
   */
  @Select("select * from user")
  @Results(
      id = "userMap",
      value = {
        @Result(id = true, column = "id", property = "userId"),
        @Result(column = "username", property = "userName"),
        @Result(column = "sex", property = "userSex"),
        @Result(column = "address", property = "userAddress"),
        @Result(column = "birthday", property = "userBirthday")
      })
  List<User> findAll();

  /*
   * 根据 id 查询一个用户
   * @param userId
   * @return
   */
  @Select("select * from user where id = #{uid} ")
  @ResultMap("userMap")
  User findById(Integer userId);

  /**
   * 保存操作
   *
   * @param user
   * @return
   */
  @Insert(
      "insert into user (username,sex,birthday,address) values(#{username},#{sex},#{birthday},#{address})")
  @SelectKey(
      keyColumn = "id",
      keyProperty = "id",
      resultType = Integer.class,
      before = false,
      statement = {"select last_insert_id()"})
  int saveUser(User user);
  /**
   * 更新操作
   *
   * @param user
   * @return
   */
  @Update(
      "update user set username=#{username},address=#{address},sex=#{sex},birthday=#{birthday} where id =#{id} ")
  int updateUser(User user);
  /**
   * 删除用户
   *
   * @param userId
   * @return
   */
  @Delete("delete from user where id = #{uid} ")
  int deleteUser(Integer userId);

  /**
   * 查询使用聚合函数
   *
   * @return
   */
  @Select("select count(*) from user ")
  int findTotal();

  /**
   * 模糊查询
   *
   * @param name
   * @return
   */
  @Select("select * from user where username like #{username} ")
  List<User> findByName(String name);

  /**
   * 查询所有账户，采用延迟加载的方式查询账户的所属用户
   *
   * @return
   */
  @Select("select * from account")
  @Results(
      id = "accountMap",
      value = {
        @Result(id = true, column = "id", property = "id"),
        @Result(column = "uid", property = "uid"),
        @Result(column = "money", property = "money"),
        @Result(
            column = "uid",
            property = "user",
            one = @One(select = "com.itheima.dao.IUserDao.findById", fetchType = FetchType.LAZY))
      })
  List<Account> findAllLazy();

  /**
   * 查询所有用户
   *
   * @return
   */
  @Select("select * from user")
  @Results(
      id = "userMap",
      value = {
        @Result(id = true, column = "id", property = "userId"),
        @Result(column = "username", property = "userName"),
        @Result(column = "sex", property = "userSex"),
        @Result(column = "address", property = "userAddress"),
        @Result(column = "birthday", property = "userBirthday"),
        @Result(
            column = "id",
            property = "accounts",
            many =
                @Many(select = "com.itheima.dao.IAccountDao.findByUid", fetchType = FetchType.LAZY))
      })
  List<User> findAllManyLazy();
}
