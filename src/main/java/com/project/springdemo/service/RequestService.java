package com.project.springdemo.service;

import com.project.springdemo.exception.ServiceException;
import com.project.springdemo.response.MainConsoleResponse;
import com.project.springdemo.response.RequestConsoleReport;
import com.project.springdemo.response.RequestConsoleResponse;

import java.util.List;

public interface RequestService {
    MainConsoleResponse createRequest(String title, String desc, Long minAmount,
                                      Long maxAmount, Long userId, List<String> categoryIds)throws ServiceException;

    MainConsoleResponse updateRequest(long id,String title, String desc, Long minAmount,
                                      Long maxAmount, Long userId,List<String> categoryIds)throws ServiceException;

    RequestConsoleResponse getAllRequestByUserId(Long userId, Integer page, Integer size);

    RequestConsoleReport findById(Long id) throws ServiceException;

    RequestConsoleResponse getAllRequestByCategoryCode(String categoryCode, Integer page, Integer size);
}
