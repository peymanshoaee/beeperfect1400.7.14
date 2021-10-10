package com.project.springdemo.service.impl;

import com.project.springdemo.domain.RequestCategory;
import com.project.springdemo.repository.RequestCategoryRepository;
import com.project.springdemo.service.RequestCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestCategoryServiceImpl implements RequestCategoryService {

    private final RequestCategoryRepository requestCategoryRepository;

    public RequestCategoryServiceImpl(RequestCategoryRepository requestCategoryRepository) {
        this.requestCategoryRepository = requestCategoryRepository;
    }

    public void save(RequestCategory requestCategory) {
        requestCategoryRepository.save(requestCategory);
    }

    public List<RequestCategory> getListByRequestId(Long requestId){
       return requestCategoryRepository.findByRequest_Id(requestId);
    }

    public void delete(RequestCategory requestCategory){
        requestCategoryRepository.delete(requestCategory);
    }
}
