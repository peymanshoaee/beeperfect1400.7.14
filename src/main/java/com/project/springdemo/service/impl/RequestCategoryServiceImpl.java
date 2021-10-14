package com.project.springdemo.service.impl;

import com.project.springdemo.domain.Category;
import com.project.springdemo.domain.Request;
import com.project.springdemo.domain.RequestCategory;
import com.project.springdemo.repository.RequestCategoryRepository;
import com.project.springdemo.response.RequestConsoleReport;
import com.project.springdemo.response.RequestConsoleResponse;
import com.project.springdemo.service.RequestCategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RequestCategoryServiceImpl implements RequestCategoryService {

    private final RequestCategoryRepository requestCategoryRepository;

    public RequestCategoryServiceImpl(RequestCategoryRepository requestCategoryRepository) {
        this.requestCategoryRepository = requestCategoryRepository;
    }
    @Override
    public void save(RequestCategory requestCategory) {
        requestCategoryRepository.save(requestCategory);
    }

    @Override
    public List<RequestCategory> getListByRequestId(Long requestId){
       return requestCategoryRepository.findByRequest_Id(requestId);
    }

    @Override
    public void delete(RequestCategory requestCategory){
        requestCategoryRepository.delete(requestCategory);
    }

    @Override
    public RequestConsoleResponse findAllByCategoryCode(String categoryCode, Integer page, Integer size){
        if(page == null || page == 0){
            page=0;
        }
        if(size==null || size == 0){
            size =10;
        }

        Pageable pageRequest = PageRequest.of(page, size, Sort.by("id").descending());
        if(categoryCode.equals("")){
            Page<RequestCategory> result = requestCategoryRepository.findAll(pageRequest);
            return setRequestCategory(result.getContent(),result.getTotalElements(),result.getTotalPages(), result.getNumberOfElements());
        }

        Page<RequestCategory> resultList = requestCategoryRepository.findByCategoryCode(categoryCode,pageRequest);
        return setRequestCategory(resultList.getContent(),resultList.getTotalElements(),resultList.getTotalPages(), resultList.getNumberOfElements());
    }

    public RequestConsoleResponse setRequestCategory(List<RequestCategory> requestCategoryList, long totalElements, int totalPages, int numberOfElements){
        List<RequestConsoleReport> requestConsoleReportList=new ArrayList<>();
        for(RequestCategory requestCategory:requestCategoryList){

            RequestConsoleReport value=new RequestConsoleReport();
            value.setId(requestCategory.getRequest().getId());
            value.setTitle(requestCategory.getRequest().getTitle());
            value.setDescription(requestCategory.getRequest().getDescription());
            value.setMinAmount(requestCategory.getRequest().getMinAmount());
            value.setMaxAmount(requestCategory.getRequest().getMaxAmount());
            value.setUserId(requestCategory.getRequest().getUser().getId());
            value.setUsername(requestCategory.getRequest().getUser().getUsername());


            List<Category> categoryList=new ArrayList<>();
            categoryList.add(requestCategory.getCategory());

            if(categoryList.size()!=0){
                value.setCategoryList(categoryList);
            }
            value.setCategoryCount(categoryList.size());
            requestConsoleReportList.add(value);
        }

        RequestConsoleResponse response=new RequestConsoleResponse();
        response.setNumberOfElements(numberOfElements);
        response.setTotalElements(totalElements);
        response.setTotalPages(totalPages);
        response.setRequestConsoleReportList(requestConsoleReportList);
        return response;

    }
}
