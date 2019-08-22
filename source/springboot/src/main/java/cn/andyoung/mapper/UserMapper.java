package cn.andyoung.mapper;

import cn.andyoung.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

  List<User> queryUserList();
}
