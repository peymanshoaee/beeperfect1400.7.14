package com.project.springdemo.repository;

import com.project.springdemo.domain.Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    Request findById(long id);
    Page<Request> findByUser_Id(Long user_Id, Pageable pageable);

}
