package com.project.springdemo.service;

import com.project.springdemo.domain.Category;

import java.util.Optional;

public interface CategoryService {
    Optional<Category> findById(Long id);
}
