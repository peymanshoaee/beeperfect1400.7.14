package com.project.springdemo.service.impl;

import com.project.springdemo.domain.Status;
import com.project.springdemo.repository.StatusRepository;
import com.project.springdemo.service.StatusService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatusServiceImpl implements StatusService {
    private final Logger log = LogManager.getLogger(getClass());
    private final StatusRepository statusRepository;

    public StatusServiceImpl(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    @Override
    public Status findByCode(String code) {
        return statusRepository.findByCode(code);

    }
}
