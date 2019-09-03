package cn.andyoung.dao.impl;

import cn.andyoung.dao.IUserDao;
import cn.andyoung.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

public class UserDaoImplTest {
  private InputStream in;
  private SqlSessionFactory factory;
  private IUserDao userDao;

  @Before
  public void setUp() throws Exception {
    // 1.读取配置文件
    in = Resources.getResourceAsStream("SqlMapConfig.xml");
    // 2.创建构建者对象
    SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
    // 3.创建 SqlSession 工厂对象
    factory = builder.build(in);
    // 4.创建 Dao 接口的实现类
    userDao = new UserDaoImpl(factory);
  }

  @After
  public void tearDown() throws Exception {
    // 7.释放资源
    in.close();
  }

  @Test
  public void findAll() {
    List<User> users = userDao.findAll();
    for (User user : users) {
      System.out.println(user);
    }
  }

  @Test
  public void findById() {
    User user = userDao.findById(1);
    System.out.println(user);
  }

  @Test
  public void saveUser() {
    User user = new User();
    user.setUsername("add user" + System.currentTimeMillis());
    user.setSex("n");
    user.setAddress("nb");
    user.setBirthday(new Date());
    int res = userDao.saveUser(user);
    System.out.println(res);
  }

  @Test
  public void updateUser() {
    User user = userDao.findById(1);
    user.setAddress("new address");
    int res = userDao.saveUser(user);
    System.out.println(res);
  }

  @Test
  public void deleteUser() {
    // 6.执行操作
    int res = userDao.deleteUser(56);
    System.out.println(res);
  }

  @Test
  public void findTotal() {
    // 6.执行操作
    int res = userDao.findTotal();
    System.out.println(res);
  }
}
