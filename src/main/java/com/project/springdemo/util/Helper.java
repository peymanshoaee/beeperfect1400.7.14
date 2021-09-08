package com.project.springdemo.util;

import com.project.springdemo.response.*;
import com.project.springdemo.service.StatusService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class Helper {

    private final StatusService statusService;


    public Helper(StatusService statusService) {
        this.statusService = statusService;


    }

    public MainConsoleResponse fillMainConsoleResponse(int result){
        MainConsoleResponse mainConsoleResponse =new MainConsoleResponse();
        mainConsoleResponse.setResult(String.valueOf(result));
        mainConsoleResponse.setDoTime(DateUtils.getLocaleDate(DateUtils.FARSI_LOCALE, new Date(), "yyyy/MM/dd HH:mm:ss", false));
        mainConsoleResponse.setDescription(statusService.findByCode(String.valueOf(result)).getDescription());
        return mainConsoleResponse;
    }

    public <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        ModelMapper modelMapper=new ModelMapper();
        return source
                .stream()
                .map(element -> modelMapper.map(element, targetClass))
                .collect(Collectors.toList());
    }

    public LoginResponse fillResponseToken(int result, Date doTime, String token, long expireTime){
        LoginResponse response = new LoginResponse();

        response.setResult(String.valueOf(result));
        response.setDescription(statusService.findByCode(String.valueOf(result)).getDescription());
        response.setDoTime(DateUtils.getLocaleDate(DateUtils.FARSI_LOCALE, doTime, "yyyy/MM/dd HH:mm:ss", false));
        response.setToken(token);
        response.setExpireTime(expireTime);

        return response;
    }

    public UserResponse fillUserResponse(UserConsoleReport userConsoleReport,int result){
        UserResponse userResponse= new UserResponse();
        userResponse.setUserConsoleReport(userConsoleReport);
        userResponse.setResult(String.valueOf(result));
        userResponse.setDescription(statusService.findByCode(String.valueOf(result)).getDescription());
        userResponse.setDoTime(DateUtils.getLocaleDate(DateUtils.FARSI_LOCALE, new Date(), "yyyy/MM/dd HH:mm:ss", false));

        return userResponse;
    }

    public RequestResponse fillRequestResponse(RequestConsoleReport requestConsoleReport, int result){
        RequestResponse requestResponse= new RequestResponse();
        requestResponse.setRequestConsoleReport(requestConsoleReport);
        requestResponse.setResult(String.valueOf(result));
        requestResponse.setDescription(statusService.findByCode(String.valueOf(result)).getDescription());
        requestResponse.setDoTime(DateUtils.getLocaleDate(DateUtils.FARSI_LOCALE, new Date(), "yyyy/MM/dd HH:mm:ss", false));

        return requestResponse;
    }



}
