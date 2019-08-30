package cn.andyoung.druid4mybatis.dao;

import cn.andyoung.druid4mybatis.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IUserDao {

  public List<User> findAll();
}
