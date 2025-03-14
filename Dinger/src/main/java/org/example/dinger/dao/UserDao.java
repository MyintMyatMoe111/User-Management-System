package org.example.dinger.dao;

import org.example.dinger.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserDao extends JpaRepository<User, Integer> {
    @Query("""
            select u from User u where u.userName = ?1
            """)
    Optional<User> findUsername(String userNameOrEmail);

    Optional<User> findById(Integer userId);

    boolean existsByUserName(String userName);

}
