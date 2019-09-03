package cn.andyoung.transactional.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface IAccount {
  @Update("UPDATE account set money=#{money} WHERE id=#{id}")
  int update(@Param("money") double money, @Param("id") int id);
}
