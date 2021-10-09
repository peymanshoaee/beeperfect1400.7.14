package com.project.springdemo.service.impl;

import com.project.springdemo.domain.User;
import com.project.springdemo.exception.ServiceException;
import com.project.springdemo.jwt.JwtUtils;
import com.project.springdemo.repository.UserRepository;
import com.project.springdemo.request.UserRequest;
import com.project.springdemo.response.LoginResponse;
import com.project.springdemo.response.MainConsoleResponse;
import com.project.springdemo.response.UserConsoleReport;
import com.project.springdemo.response.UserConsoleResponse;
import com.project.springdemo.service.ExceptionError;
import com.project.springdemo.service.UserService;
import com.project.springdemo.util.Helper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final Logger log = LogManager.getLogger(getClass());
    private final UserRepository userRepository;
    private final Helper helper;
    private final JavaMailSender javaMailSender;
    private final com.project.springdemo.jwt.JwtUtils jwtUtils;


    public UserServiceImpl(UserRepository userRepository, Helper helper, JavaMailSender javaMailSender, JwtUtils JwtUtils) {
        this.userRepository = userRepository;
        this.helper = helper;
        this.javaMailSender = javaMailSender;
        this.jwtUtils = JwtUtils;

    }


    @Override
    public MainConsoleResponse createUser(String username, String password, boolean enabled,
                                          String firstName, String lastName, String nickName,
                                          boolean business,
                                          MultipartFile imageFile) throws ServiceException, IOException {
        User user=new User();
        if(username!=null && !username.equals("")){
            if(userRepository.findByUsername(username)!=null){
                log.error("User is Exist");
                throw new ServiceException("User is Exist", ExceptionError.USER_MODEL_USERNAME_IS_EXIST);
            }
            user.setUsername(username);
        }
        if(password==null || password.equals("")){
            log.error("Password Is Null");
            throw new ServiceException("Password Is Null", ExceptionError.USER_MODEL_PASSWORD_IS_NULL);
        }
        user.setPassword(password);
        user.setEnabled(enabled);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setNickName(nickName);
        user.setBusiness(business);

        //region<Save Image to DB>
        //String imgPath="C:\\Users\\Home\\Desktop\\Sample\\13.jpg";
        //FileInputStream imageStream=new FileInputStream(imgPath);
        if(!imageFile.isEmpty()){
            byte[] data=imageFile.getBytes();
            //String imageString= Base64.getEncoder().encodeToString(data);
            user.setImage(data);

            //endregion
            Path currentPath= Paths.get(".");
            Path absolutePath=currentPath.toAbsolutePath();
            user.setUrl(absolutePath+"/src/main/resources/static/photos/"+user.getUsername()+".jpg");
        }



        userRepository.save(user);

        return helper.fillMainConsoleResponse(ExceptionError.SUCCESSFUL);

    }

    @Override
    public MainConsoleResponse updateUser(Long id,String username, String password,boolean enabled,
                                          String firstName, String lastName,String nickName,
                                          MultipartFile imageFile) throws ServiceException, IOException {
        User user=userRepository.findById(id).orElseThrow(() ->{
            log.error("User Not Found");
            return new ServiceException("Users Not Found", ExceptionError.USER_MODEL_NOT_FOUND);
        });
        Files.deleteIfExists(Paths.get(user.getUrl()));

        if(username!=null && !username.equals("")){
            if(userRepository.findByUsername(username)!=null){
                log.error("UserName  Is already Exist");
                throw new ServiceException("Channel name Is already Exist", ExceptionError.USER_MODEL_USERNAME_IS_EXIST);
            }
            user.setUsername(username.toLowerCase());
            Path currentPath= Paths.get(".");
            Path absolutePath=currentPath.toAbsolutePath();
            user.setUrl(absolutePath+"/src/main/resources/static/photos/"+user.getUsername()+".jpg");
        }
        if(password!=null && !password.equals("")){
            user.setPassword(password);
        }
        if(firstName!=null && !firstName.equals("")){
            user.setFirstName(firstName);
        }
        if(lastName!=null && !lastName.equals("")){
            user.setLastName(lastName);
        }
        if(nickName!=null && !nickName.equals("")){
            user.setNickName(nickName);
        }



        //region<Save Image to DB>
        if(!imageFile.isEmpty()){
            //String imgPath="C:\\Users\\Home\\Desktop\\Sample\\15.jpg";
            //FileInputStream imageStream=new FileInputStream(imgPath);
            byte[] data=imageFile.getBytes();
            //String imageString= Base64.getEncoder().encodeToString(data);
            user.setImage(data);

            //endregion
            Path currentPath= Paths.get(".");
            Path absolutePath=currentPath.toAbsolutePath();
            user.setUrl(absolutePath+"/src/main/resources/static/photos/"+user.getUsername()+".jpg");
        }



        user.setEnabled(enabled);
        userRepository.save(user);
        return helper.fillMainConsoleResponse(ExceptionError.SUCCESSFUL);

    }

    @Override
    public UserConsoleReport findById(Long id) throws ServiceException, IOException {
        return convertToJson(userRepository.findById(id).orElseThrow(() -> new ServiceException("user not found" , ExceptionError.USER_MODEL_NOT_FOUND)));

    }

    @Override
    public UserConsoleReport findByUserNameAndPassword(String username,String password) throws ServiceException, IOException {
        User user=userRepository.findByUsernameAndPassword(username,password);
        if(user==null){
            log.error("User Not Found");
            throw new ServiceException("User Not Found", ExceptionError.USER_MODEL_NOT_FOUND);
        }
        return convertToJson(user);

    }

    @Override
    public MainConsoleResponse deleteUser(Long id) throws ServiceException, IOException {
        User user=userRepository.findById(id).orElseThrow(() ->{
            log.error("User Not Found");
            return new ServiceException("Users Not Found", ExceptionError.USER_MODEL_NOT_FOUND);
        });
        Files.deleteIfExists(Paths.get(user.getUrl()));
        user.setEnabled(false);
        userRepository.save(user);
        return helper.fillMainConsoleResponse(ExceptionError.SUCCESSFUL);
    }

    //@PreAuthorize("#user.username != authentication.name")
    @Override
    public void deleteByUser(UserRequest user ){
        userRepository.deleteById(user.getId());

    }

    @Override
    public UserConsoleResponse getUserList(String username, Integer page, Integer size) {
        if(page == null){
            page=0;
        }
        if(size==null || size == 0){
            size =10;
        }
        Pageable pageRequest = PageRequest.of(page, size, Sort.by("id").descending());
        if(username==null || username.equals("")){
            Page<User> result = userRepository.findAll(pageRequest);
            return setUser(result.getContent(),result.getTotalElements(),result.getTotalPages(), result.getNumberOfElements());
        }

        Page<User> result = userRepository.findByUsername(username.toLowerCase(),pageRequest);
        return setUser(result.getContent(),result.getTotalElements(),result.getTotalPages(), result.getNumberOfElements());
    }

    public UserConsoleReport convertToJson(User user) throws IOException {
        //Save Image in Disk
        UserConsoleReport userConsoleReport=new UserConsoleReport();
        if(user.getUrl()!=null && !user.getUrl().equals("")){
            File file=new File(String.valueOf(Paths.get(user.getUrl())));
            if(!file.exists()){
                FileOutputStream fileOutputStream=new FileOutputStream(user.getUrl());
                fileOutputStream.write(user.getImage());
                fileOutputStream.close();
            }
            String url=user.getUrl();
            url=url.replace("./","");
            url=url.replace("\\","/");
            userConsoleReport.setUrl(url);

        }

        //

        userConsoleReport.setId(user.getId());
        userConsoleReport.setUsername(user.getUsername());
        userConsoleReport.setPassword(user.getPassword());
        userConsoleReport.setFirstName(user.getFirstName());
        userConsoleReport.setLastName(user.getLastName());
        userConsoleReport.setNickName(user.getNickName());
        userConsoleReport.setEnabled(user.isEnabled());


        userConsoleReport.setNickName(user.getNickName());

        return userConsoleReport;
    }

    private UserConsoleResponse setUser(List<User> result,long totalElements,int totalPages,int numberOfElements){
        List<UserConsoleReport> userConsoleReportList=new ArrayList<>();
        for(User request:result ){
            UserConsoleReport value=new UserConsoleReport();
            value.setId(request.getId());
            value.setUsername(request.getUsername());
            value.setPassword(request.getPassword());
            value.setEnabled(request.isEnabled());
            value.setFirstName(request.getFirstName());
            value.setLastName(request.getLastName());
            value.setUrl(request.getUrl());
            value.setNickName(request.getNickName());
            String url=value.getUrl();
            if(url!=null && !url.equals("")) {
                url = url.replace("./", "");
                url = url.replace("\\", "/");
                value.setUrl(url);
                value.setId(request.getId());
            }

            userConsoleReportList.add(value);
        }
        UserConsoleResponse response=new UserConsoleResponse();
        response.setNumberOfElements(numberOfElements);
        response.setTotalElements(totalElements);
        response.setTotalPages(totalPages);
        response.setUserConsoleReportList(userConsoleReportList);
        return response;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    @Override
    public MainConsoleResponse retrieveImage(Long id) throws ServiceException, IOException {
        User users=userRepository.findById(id).orElseThrow(() ->{
            log.error("User Not Found");
            return new ServiceException("Users Not Found", ExceptionError.USER_MODEL_NOT_FOUND);
        });
        //users.getImage();
        //byte[] data=Base64.getEncoder().encode(users.getImage());
        String savePath="C:\\Users\\Home\\Desktop\\newImage.jpg";
        FileOutputStream fileOutputStream=new FileOutputStream(savePath);
        fileOutputStream.write(users.getImage());
        fileOutputStream.close();
        return helper.fillMainConsoleResponse(ExceptionError.SUCCESSFUL);

    }

    @Override
    public User getUserById(Long id){
        return userRepository.getById(id);
    }

    @Override
    public LoginResponse createTempUser(String username, String password, String firstName, String lastName, boolean tos ,boolean business)throws ServiceException{
        if(tos==true){

            if(username!=null && !username.equals("")){
                if(userRepository.findByUsername(username)!=null){
                    log.error("User is Exist");
                    throw new ServiceException("User is Exist", ExceptionError.USER_MODEL_USERNAME_IS_EXIST);
                }

            }
            if(password==null || password.equals("")){
                log.error("Password Is Null");
                throw new ServiceException("Password Is Null", ExceptionError.USER_MODEL_PASSWORD_IS_NULL);
            }

            User user=new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setTos(tos);
            user.setEnabled(false);
            user.setBusiness(business);
            Random rand = new Random();
            int resRandom = rand.nextInt((9999 - 100) + 1) + 10;
            user.setVerifyCode(String.valueOf(resRandom));
            Map<String, String> token=jwtUtils.generateToken(user.getUsername());
            user.setToken(token.get("token"));
            //user.setId(2L);
            userRepository.save(user);

            return helper.fillResponseToken(ExceptionError.SUCCESSFUL,new Date(),token.get("token"),Long.parseLong(token.get("expireTime")));
        }
        return helper.fillResponseToken(ExceptionError.USER_TOS_IS_REQUIRED,new Date(),"",-1);
    }

    public void sendEmail(){
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("1@gmail.com", "2@yahoo.com");

        msg.setSubject("Testing from Spring Boot");
        msg.setText("Hello World \n Spring Boot Email");

        javaMailSender.send(msg);
    }

    @Override
    public LoginResponse verifyUser(String token,String verifyCode) throws ServiceException{
        String userName=jwtUtils.getUserName(token);
        User user=userRepository.findByUsername(userName);
        if(user==null){
            log.error("User Not Exist");
            throw new ServiceException("User Not Exist", ExceptionError.USER_MODEL_NOT_FOUND);
        }
        if(!user.getVerifyCode().equals(verifyCode)){
            log.error("VerifyCode Wrong");
            throw new ServiceException("VerifyCode Wrong", ExceptionError.USER_VERIFY_CODE_IS_WRONG);
        }
        user.setEnabled(true);


        /*try {
            manager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        }catch (Exception e){
            //return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            return helper.fillResponseToken(ExceptionError.BAD_REQUEST,new Date(),"",-1);
        }*/
        Map<String, String> tokenGenerate=jwtUtils.generateToken(user.getUsername());
        user.setToken(tokenGenerate.get("token"));
        userRepository.save(user);
        return helper.fillResponseToken(ExceptionError.SUCCESSFUL,new Date(),tokenGenerate.get("token"),Long.parseLong(tokenGenerate.get("expireTime")));
    }
}
