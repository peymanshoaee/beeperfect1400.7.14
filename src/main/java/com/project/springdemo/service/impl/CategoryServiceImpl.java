package com.project.springdemo.service.impl;

import com.project.springdemo.domain.Category;
import com.project.springdemo.repository.CategoryRepository;
import com.project.springdemo.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Optional<Category> findById(Long id){
        return categoryRepository.findById(id);
    }
}
