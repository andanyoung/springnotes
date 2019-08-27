package cn.andyoung.dao;

import cn.andyoung.domain.QueryVo;
import cn.andyoung.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

public class IUserDaoTest {

  private InputStream in;
  private SqlSessionFactoryBuilder builder;
  private SqlSessionFactory factory;
  private SqlSession session;
  private IUserDao userDao;

  @Before
  public void setUp() throws Exception {
    System.out.println("this is brfore....");
    // 1.读取配置文件
    in = Resources.getResourceAsStream("SqlMapConfig.xml");
    // 2.创建 SqlSessionFactory 的构建者对象
    builder = new SqlSessionFactoryBuilder();
    // 3.使用构建者创建工厂对象 SqlSessionFactory
    factory = builder.build(in);
    // 4.使用 SqlSessionFactory 生产 SqlSession 对象
    session = factory.openSession();
    // 5.使用 SqlSession 创建 dao 接口的代理对象
    userDao = session.getMapper(IUserDao.class);
  }

  @After // 在测试方法执行完成之后执行
  public void destroy() throws Exception {
    session.commit();
    // 7.释放资源
    session.close();
    in.close();
  }

  @Test
  public void testFindAll() throws Exception {
    System.out.println("testFindAll!");
    // 6.使用代理对象执行查询所有方法
    List<User> users = userDao.findAll();
    for (User user : users) {
      System.out.println(user);
    }
    // 7.释放资源
    session.close();
    in.close();
  }

  @Test
  public void findById() {
    System.out.println("findById!");
    User user = userDao.findById(1);
    System.out.println(user);
  }

  @Test
  public void testSave() {
    User user = new User();
    user.setUsername("modify user name");
    user.setAddress("nb");
    user.setSex("男");
    user.setBirthday(new Date());

    System.out.println("保存操作之前：" + user);
    int res = userDao.saveUser(user);
    System.out.println("保存操作之后：" + user + res);
  }

  @Test
  public void testUpdateUser() throws Exception {
    // 1.根据 id 查询
    User user = userDao.findById(1);
    System.out.println("Update操作之前：" + user);
    // 2.更新操作
    user.setAddress("北京市132");
    int res = userDao.updateUser(user);
    System.out.println("Update操作之后：" + user);
    System.out.println(res);
  }

  @Test
  public void testDeleteUser() throws Exception {

    User user = new User();
    user.setUsername("modify user name");
    user.setAddress("nb");
    user.setSex("男");
    user.setBirthday(new Date());

    System.out.println("保存操作之前：" + user);
    userDao.saveUser(user);
    System.out.println("保存操作之后：" + user);
    // 6.执行操作
    //    userDao.saveUser();
    int res = userDao.deleteUser(user.getId());
    System.out.println(res);
  }

  @Test
  public void testFindByName() {

    // 5.执行查询一个方法
    List<User> users = userDao.findByName("%z%");
    for (User user : users) {
      System.out.println(user);
    }
  }

  @Test
  public void testFindTotal() {
    // 6.执行操作
    int res = userDao.findTotal();
    System.out.println(res);
  }

  @Test
  public void testFindByQueryVo() {
    QueryVo vo = new QueryVo();
    User user = new User();
    user.setUsername("%u%");
    vo.setUser(user);
    List<User> users = userDao.findByVo(vo);
    for (User u : users) {
      System.out.println(u);
    }
  }
}
