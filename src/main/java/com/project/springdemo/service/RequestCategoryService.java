package com.project.springdemo.service;

import com.project.springdemo.domain.RequestCategory;
import com.project.springdemo.response.RequestConsoleResponse;

import java.util.List;

public interface RequestCategoryService {

    void save(RequestCategory requestCategory);
    List<RequestCategory> getListByRequestId(Long requestId);
    void delete(RequestCategory requestCategory);
    RequestConsoleResponse findAllByCategoryCode(String categoryCode, Integer page, Integer size);
}
