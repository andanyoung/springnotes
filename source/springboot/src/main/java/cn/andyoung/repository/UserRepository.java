package cn.andyoung.repository;

import cn.andyoung.domain.JPAUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<JPAUser, Long> {
  public List<JPAUser> findAll();
}
