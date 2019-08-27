package cn.andyoung.dao.impl;

import cn.andyoung.dao.IUserDao;
import cn.andyoung.domain.QueryVo;
import cn.andyoung.domain.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;
/**
 * Title: UserDaoImpl
 *
 * <p>Description: dao 的实现类
 *
 * <p>Company: http://www.itheima.com/
 */
public class UserDaoImpl implements IUserDao {

  private SqlSessionFactory factory;

  public UserDaoImpl(SqlSessionFactory factory) {
    this.factory = factory;
  }

  @Override
  public List<User> findAll() {
    SqlSession session = factory.openSession();
    List<User> users = session.selectList("cn.andyoung.dao.IUserDao.findAll");
    session.close();
    return users;
  }

  @Override
  public User findById(Integer userId) {
    SqlSession session = factory.openSession();
    User user = session.selectOne("cn.andyoung.dao.IUserDao.findById", userId);
    session.close();
    return user;
  }

  @Override
  public int saveUser(User user) {
    SqlSession session = factory.openSession();
    int res = session.insert("cn.andyoung.dao.IUserDao.saveUser", user);
    session.commit();
    session.close();
    return res;
  }

  @Override
  public int updateUser(User user) {
    SqlSession session = factory.openSession();
    int res = session.update("cn.andyoung.dao.IUserDao.updateUser", user);
    session.commit();
    session.close();
    return res;
  }

  @Override
  public int deleteUser(Integer userId) {
    SqlSession session = factory.openSession();
    int res = session.delete("cn.andyoung.dao.IUserDao.deleteUser", userId);
    session.commit();
    session.close();
    return res;
  }

  @Override
  public int findTotal() {
    SqlSession session = factory.openSession();
    int res = session.selectOne("cn.andyoung.dao.IUserDao.findTotal");
    session.close();
    return res;
  }

  @Override
  public List<User> findByVo(QueryVo vo) {
    return null;
  }

  @Override
  public List<User> findByName(String username) {
    return null;
  }
}
