package cn.andyoung.dao;

import cn.andyoung.domain.Role;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

public class IRoleDaoTest {

  private InputStream in;
  private SqlSessionFactoryBuilder builder;
  private SqlSessionFactory factory;
  private SqlSession session;
  private IRoleDao roleDao;

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
    roleDao = session.getMapper(IRoleDao.class);
  }

  @After // 在测试方法执行完成之后执行
  public void destroy() throws Exception {
    session.commit();
    // 7.释放资源
    session.close();
    in.close();
  }

  /** 测试查询所有 */
  @Test
  public void testFindAll() {
    List<Role> roles = roleDao.findAll();
    for (Role role : roles) {
      System.out.println("---每个角色的信息----");
      System.out.println(role);
      System.out.println(role.getUsers());
    }
  }
}
