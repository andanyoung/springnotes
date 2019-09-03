package cn.andyoung.mybatis.pagehelper.dao;

import cn.andyoung.mybatis.pagehelper.domain.User;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface IUser {
  @Select("select * From User")
  Page<User> findUsers();
}
