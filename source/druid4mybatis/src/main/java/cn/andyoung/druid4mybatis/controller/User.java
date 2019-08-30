package cn.andyoung.druid4mybatis.controller;

import cn.andyoung.druid4mybatis.dao.IUserDao;
import com.alibaba.druid.stat.DruidStatManagerFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class User {
  @Autowired private IUserDao userDao;

  @RequestMapping("/user")
  public List<cn.andyoung.druid4mybatis.domain.User> index() {
    List<cn.andyoung.druid4mybatis.domain.User> all = userDao.findAll();
    System.out.println(all);
    return all;
  }

  @GetMapping("/stat")
  public Object druidStat() {
    // DruidStatManagerFacade#getDataSourceStatDataList 该方法可以获取所有数据源的监控数据
    return DruidStatManagerFacade.getInstance().getDataSourceStatDataList();
  }
}
