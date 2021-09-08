package com.project.springdemo.repository;


import com.project.springdemo.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long> {

    Status findByCode(String code);
}
