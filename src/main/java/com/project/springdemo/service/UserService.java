package com.project.springdemo.service;

import com.project.springdemo.domain.User;
import com.project.springdemo.exception.ServiceException;
import com.project.springdemo.request.UserRequest;
import com.project.springdemo.response.LoginResponse;
import com.project.springdemo.response.MainConsoleResponse;
import com.project.springdemo.response.UserConsoleReport;
import com.project.springdemo.response.UserConsoleResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface UserService  {
    UserConsoleReport findById(Long id) throws ServiceException, IOException;
    MainConsoleResponse createUser(String username, String password, boolean enabled, String firstName, String lastName, String nickName, boolean business,MultipartFile imageFile ) throws ServiceException, IOException;
    MainConsoleResponse updateUser(Long id,String username,String password,boolean enabled, String firstName, String lastName,String nickName,MultipartFile imageFile) throws ServiceException, IOException;
    MainConsoleResponse deleteUser(Long id) throws ServiceException, IOException;
    UserConsoleResponse getUserList(String username, Integer page, Integer size);
    void deleteByUser(UserRequest user );
    MainConsoleResponse retrieveImage(Long id) throws ServiceException, IOException;
    User getUserById(Long id);
    UserConsoleReport findByUserNameAndPassword(String username,String password) throws ServiceException, IOException;
    LoginResponse createTempUser(String username, String password, String firstName, String lastName, boolean tos,boolean business)throws ServiceException;
    LoginResponse verifyUser(String token,String verifyCode) throws ServiceException;
}
