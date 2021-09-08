package com.project.springdemo.service;

import com.project.springdemo.domain.Status;

public interface StatusService {
    Status findByCode(String code);
}
