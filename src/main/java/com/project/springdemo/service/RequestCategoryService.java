package com.project.springdemo.service;

import com.project.springdemo.domain.RequestCategory;

import java.util.List;

public interface RequestCategoryService {

    void save(RequestCategory requestCategory);
    List<RequestCategory> getListByRequestId(Long requestId);
    void delete(RequestCategory requestCategory);
}
