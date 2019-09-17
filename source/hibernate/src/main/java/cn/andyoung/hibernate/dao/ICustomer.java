package cn.andyoung.hibernate.dao;

import cn.andyoung.hibernate.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICustomer extends JpaRepository<Customer, Long> {}
