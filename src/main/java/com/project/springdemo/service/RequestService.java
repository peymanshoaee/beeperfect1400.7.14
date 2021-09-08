package com.project.springdemo.service;

import com.project.springdemo.exception.ServiceException;
import com.project.springdemo.response.MainConsoleResponse;
import com.project.springdemo.response.RequestConsoleReport;
import com.project.springdemo.response.RequestConsoleResponse;

public interface RequestService {

    MainConsoleResponse createRequest(String title, String desc, Long minAmount,
                                      Long maxAmount, Long userId)throws ServiceException;

    MainConsoleResponse updateRequest(Long id,String title, String desc, Long minAmount,
                                      Long maxAmount, Long userId)throws ServiceException;

    RequestConsoleResponse getAllRequestByUserId(Long userId, Integer page, Integer size);

    RequestConsoleReport findById(Long id) throws ServiceException;
}
