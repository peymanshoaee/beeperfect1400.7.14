package com.project.springdemo.service.impl;

import com.project.springdemo.domain.Category;
import com.project.springdemo.domain.Request;
import com.project.springdemo.domain.RequestCategory;
import com.project.springdemo.domain.User;
import com.project.springdemo.exception.ServiceException;
import com.project.springdemo.repository.RequestRepository;
import com.project.springdemo.response.*;
import com.project.springdemo.service.*;
import com.project.springdemo.util.Helper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RequestServiceImpl implements RequestService{
    private final UserService userService;
    private final Logger log = LogManager.getLogger(getClass());
    private final Helper helper;
    private final RequestRepository requestRepository;
    private final CategoryService categoryService;
    private final RequestCategoryService requestCategoryService;


    public RequestServiceImpl(UserService userService, Helper helper, RequestRepository requestRepository, CategoryService categoryService, RequestCategoryService requestCategoryService) {
        this.userService = userService;
        this.helper = helper;
        this.requestRepository = requestRepository;
        this.categoryService = categoryService;
        this.requestCategoryService = requestCategoryService;
    }
    @Override
    public MainConsoleResponse createRequest(String title, String desc, Long minAmount,
                                             Long maxAmount, Long userId,List<String> categoryIds)throws ServiceException{
        List<Category> categoryList=new ArrayList<>();

        User user=userService.getUserById(userId);
        if(user==null){
            log.error("User Not Exist");
            throw new ServiceException("Category is Null", ExceptionError.CATEGORY_MODEL_NOT_FOUND);
        }
        if(categoryIds.size()==0){
            log.error("User Not Exist");
            throw new ServiceException("User is Exist", ExceptionError.USER_MODEL_NOT_FOUND);
        }
        for (String strCategoryId : categoryIds){
            Long categoryId=Long.valueOf(strCategoryId);
            Optional<Category> category=categoryService.findById(categoryId);
            if(category.isEmpty()){
                log.error("Category is Null");
                throw new ServiceException("Category is Null", ExceptionError.CATEGORY_MODEL_NOT_FOUND);
            }
            categoryList.add(category.get());


        }
        Request request=new Request();

        request.setUser(user);


        if(title==null || title.equals("")){
            log.error("Title is Null");
            throw new ServiceException("Title is Null", ExceptionError.REQUEST_MODEL_TITLE_IS_NULL);
        }
        request.setTitle(title);
        if( !desc.equals("")){
            request.setDescription(desc);
        }
        if(minAmount!=0 && minAmount>0){
            request.setMaxAmount(minAmount);
        }
        if(maxAmount!=0 && maxAmount>0){
            request.setMaxAmount(maxAmount);
        }


        request.setCreateDate(LocalDate.now());

        requestRepository.save(request);

        for(Category category:categoryList){
            RequestCategory requestCategory=new RequestCategory();
            requestCategory.setCategory(category);
            requestCategory.setRequest(request);
            requestCategory.setCreationDate(new Date());
            requestCategoryService.save(requestCategory);
        }

        return helper.fillMainConsoleResponse(ExceptionError.SUCCESSFUL);
    }
    @Override
    public MainConsoleResponse updateRequest(Long id,String title,String desc,Long minAmount,
                                             Long maxAmount,Long userId)throws ServiceException{
        Request request=requestRepository.getById(id);
        if(request==null){
            log.error("Request Not Found");
            throw new ServiceException("Request Not Found", ExceptionError.REQUEST_MODEL_NOT_FOUND);
        }
        if(title!=null && !title.equals("")){
            request.setTitle(title);
        }
        if(desc!=null && !desc.equals("")){
            request.setDescription(desc);
        }
        if(minAmount!=0 && minAmount>0){
            request.setMaxAmount(minAmount);
        }
        if(maxAmount!=0 && maxAmount>0){
            request.setMaxAmount(maxAmount);
        }
        User user=userService.getUserById(userId);
        if(user==null){
            log.error("User Not Exist");
            throw new ServiceException("User is Exist", ExceptionError.USER_MODEL_NOT_FOUND);
        }
        request.setUser(user);


        requestRepository.save(request);
        return helper.fillMainConsoleResponse(ExceptionError.SUCCESSFUL);
    }

    @Override
    public RequestConsoleResponse getAllRequestByUserId(Long userId, Integer page, Integer size){
        if(page == null){
            page=0;
        }
        if(size==null || size == 0){
            size =10;
        }
        Pageable pageRequest = PageRequest.of(page, size, Sort.by("id").descending());
        if(userId==null || userId==0){
            Page<Request> result = requestRepository.findAll(pageRequest);
            return setRequest(result.getContent(),result.getTotalElements(),result.getTotalPages(), result.getNumberOfElements());
        }

        Page<Request> result = requestRepository.findByUser_Id(userId,pageRequest);
        return setRequest(result.getContent(),result.getTotalElements(),result.getTotalPages(), result.getNumberOfElements());
    }

    private RequestConsoleResponse setRequest(List<Request> result, long totalElements, int totalPages, int numberOfElements){
        List<RequestConsoleReport> requestConsoleReportList=new ArrayList<>();
        for(Request request:result ){
            RequestConsoleReport value=new RequestConsoleReport();
            value.setId(request.getId());
            value.setTitle(request.getTitle());
            value.setDescription(request.getDescription());
            value.setMinAmount(request.getMinAmount());
            value.setMaxAmount(request.getMaxAmount());
            value.setUser(request.getUser());


            requestConsoleReportList.add(value);
        }
        RequestConsoleResponse response=new RequestConsoleResponse();
        response.setNumberOfElements(numberOfElements);
        response.setTotalElements(totalElements);
        response.setTotalPages(totalPages);
        response.setRequestConsoleReportList(requestConsoleReportList);
        return response;
    }

    public RequestConsoleReport findById(Long id) throws ServiceException{
        return convertToJson(requestRepository.findById(id).orElseThrow(() -> new ServiceException("Request not found" , ExceptionError.REQUEST_MODEL_NOT_FOUND)));

    }

    public RequestConsoleReport convertToJson(Request request){
        RequestConsoleReport requestConsoleReport=new RequestConsoleReport();
        requestConsoleReport.setId(request.getId());
        requestConsoleReport.setTitle(request.getTitle());
        requestConsoleReport.setDescription(request.getDescription());
        requestConsoleReport.setMinAmount(request.getMinAmount());
        requestConsoleReport.setMaxAmount(request.getMaxAmount());
        requestConsoleReport.setUser(request.getUser());
        return requestConsoleReport;

    }
}
