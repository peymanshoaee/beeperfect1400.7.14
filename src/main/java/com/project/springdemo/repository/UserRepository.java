package com.project.springdemo.repository;

import com.project.springdemo.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String Username);
    Page<User> findByUsername(String username, Pageable pageable);
    User findByUsernameAndPassword(String userName,String Password);
}
