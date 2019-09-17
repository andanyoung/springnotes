package cn.andyoung.hibernate;

import cn.andyoung.hibernate.dao.ICustomer;
import cn.andyoung.hibernate.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HibernateApplicationTests {

  @Autowired private ICustomer customer;
  @Autowired private JpaTransactionManager transactionManager;

  @Test
  public void save() {
    EntityManagerFactory factory = Persistence.createEntityManagerFactory("myJpa");
    // 创建实体管理类
    EntityManager em = factory.createEntityManager();
    EntityTransaction transaction = em.getTransaction();
    transaction.begin();
    Customer customer = new Customer();
    customer.setCustName("andy");
    // 保存操作
    em.persist(customer);
    // 提交事务
    transaction.commit();
    // 释放资源
    em.close();
    factory.close();
  }

  @Test
  public void findAll() {
    List<Customer> customers = customer.findAll();
    for (Customer customer : customers) {
      System.out.println(customer);
    }
  }

  @Test
  public void saveModel() {

    Customer c = new Customer();
    c.setCustName("save");
    Customer save = customer.save(c);
    System.out.println(save);
  }
}
