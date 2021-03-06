package com.project.springdemo.repository;

import com.project.springdemo.domain.Category;
import com.project.springdemo.domain.RequestCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestCategoryRepository extends JpaRepository<RequestCategory,Long> {
    List<RequestCategory>findByRequest_Id(Long requestId);
    Page<RequestCategory>findByCategoryCode(String categoryCode, Pageable pageable);
}
