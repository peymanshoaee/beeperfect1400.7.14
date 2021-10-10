package com.project.springdemo;

import com.project.springdemo.exception.ServiceException;
import com.project.springdemo.jwt.JwtAuth;
import com.project.springdemo.jwt.JwtUtils;
import com.project.springdemo.request.*;
import com.project.springdemo.response.*;
import com.project.springdemo.service.ExceptionError;
import com.project.springdemo.service.RequestService;
import com.project.springdemo.service.UserService;
import com.project.springdemo.util.Helper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

@RestController
//@RequestMapping(path = "/api/console")
public class MainController {

    private final UserService userService;
    private final Helper helper;
    private final Logger log = LogManager.getLogger(MainController.class);
    private static final String LOG_SERVICE_EXCEPTION = "service exception is ===> {}";
    private final AuthenticationManager manager;
    private final JwtUtils jwtUtils;
    private final RequestService requestService;


    public MainController(Helper helper, UserService userService, AuthenticationManager manager, JwtUtils jwtUtils, RequestService requestService) {

        this.helper=helper;

        this.userService = userService;
        this.manager = manager;
        this.jwtUtils = jwtUtils;
        //this.requestService = requestService;
        this.requestService = requestService;
    }


    //region <Jwt Login>

    @PostMapping("/jwt/login")
    public @ResponseBody
    ResponseEntity<LoginResponse> jwtLogin(@RequestBody JwtAuth jwtAuth, HttpServletResponse response){

        try {
            manager.authenticate(new UsernamePasswordAuthenticationToken(jwtAuth.getUsername(),jwtAuth.getPassword()));
        }catch (Exception e){
            //return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(helper.fillResponseToken(ExceptionError.BAD_REQUEST,new Date(),"",-1));
        }

        Map<String, String> token=jwtUtils.generateToken(jwtAuth.getUsername());

        response.addHeader("Authorization",token.get("token") );
        //return new ResponseEntity<>(HttpStatus.OK);
        return ResponseEntity.status(HttpStatus.OK).body(helper.fillResponseToken(ExceptionError.SUCCESSFUL,new Date(),token.get("token"),Long.parseLong(token.get("expireTime"))));
    }

    //endregion

    //region <User End Point>

    @PostMapping(value = "/user/createTemp")
    public @ResponseBody
    ResponseEntity<LoginResponse> createTempUser(@RequestBody UserTempRequest model){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.createTempUser(model.getUsername(),model.getPassword(),model.getFirstName(),model.getLastName(),model.isTos(),model.isBusiness()));
        } catch (ServiceException  e) {
            log.error(LOG_SERVICE_EXCEPTION, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(helper.fillResponseToken(e.getStatus(),new Date(),"",-1));
        }
    }

    @PostMapping(value = "/user/verifyUser")
    public @ResponseBody
    ResponseEntity<LoginResponse> verifyUser(@RequestBody RequestUserVerify model,HttpServletResponse response){
        try {
            LoginResponse loginResponse=userService.verifyUser(model.getToken(),model.getCode());
            response.addHeader("Authorization",loginResponse.getToken() );
            return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
        } catch (ServiceException  e) {
            log.error(LOG_SERVICE_EXCEPTION, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(helper.fillResponseToken(e.getStatus(),new Date(),"",-1));
        }
    }

    @GetMapping("/user/getByUsername")
    public @ResponseBody ResponseEntity<UserResponse> findUserByUserNameAndPassword(@RequestBody JwtAuth jwtAuth){
        try{
            UserConsoleReport userConsoleReport = userService.findByUserNameAndPassword(jwtAuth.getUsername(),jwtAuth.getPassword());
            return ResponseEntity.status(HttpStatus.OK).body(helper.fillUserResponse(userConsoleReport,ExceptionError.SUCCESSFUL));
        }catch (ServiceException e) {
            log.error(LOG_SERVICE_EXCEPTION, e.getMessage());
            UserConsoleReport userConsoleReport=new UserConsoleReport();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(helper.fillUserResponse(userConsoleReport,e.getStatus()));
        }catch (IOException e) {
            e.printStackTrace();
            UserConsoleReport userConsoleReport=new UserConsoleReport();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(helper.fillUserResponse(userConsoleReport,ExceptionError.RETRIEVE_IMAGE_FILE_IO_EXCEPTION));
        }
    }

    @PostMapping(value = "/user/update")
    public @ResponseBody
    ResponseEntity<MainConsoleResponse> updateUser(@RequestBody UserRequest model,@RequestParam("imageFile")MultipartFile imageFile)  {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(model.getId(),model.getUsername(),model.getPassword(),model.isEnabled(),model.getFirstName(),model.getLastName(),model.getNickName(),imageFile));
        }
        catch (ServiceException e) {
            log.error(LOG_SERVICE_EXCEPTION, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(helper.fillMainConsoleResponse(e.getStatus()));
        }
        catch (IOException e) {
            e.printStackTrace();
            log.error(LOG_SERVICE_EXCEPTION, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(helper.fillMainConsoleResponse(ExceptionError.IMAGE_FILE_IO_EXCEPTION));
        }

    }


    //@PreAuthorize("hasAuthority('OP_ACCESS_USER')")
    @PostMapping("/user/list")
    public @ResponseBody
    ResponseEntity<UserConsoleResponse> getAllUser(@RequestBody UserConsoleRequest userConsoleRequest) {
        return ResponseEntity.status(HttpStatus.OK).body( userService.getUserList(userConsoleRequest.getUsername(),userConsoleRequest.getPage(),userConsoleRequest.getSize()) );
    }

    @PostMapping(value = "/user/deleteUser/{id}")
    public ResponseEntity<MainConsoleResponse> deleteUser(@PathVariable("id") Long id)  {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.deleteUser(id));
        }
        catch (ServiceException e) {
            log.error(LOG_SERVICE_EXCEPTION, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(helper.fillMainConsoleResponse(e.getStatus()));
        }
        catch (IOException e) {
            e.printStackTrace();
            log.error(LOG_SERVICE_EXCEPTION, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(helper.fillMainConsoleResponse(ExceptionError.IMAGE_FILE_IO_EXCEPTION));
        }

    }


    @PostMapping(value = "/user/create")
    public @ResponseBody
    ResponseEntity<MainConsoleResponse> createUser(@RequestBody UserRequest model, @RequestParam("imageFile")MultipartFile imageFile)  {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.createUser(model.getUsername(),model.getPassword(),model.isEnabled(),model.getFirstName(),model.getLastName(),model.getNickName(),model.isBusiness(),imageFile));
        }
        catch (ServiceException  e) {
            log.error(LOG_SERVICE_EXCEPTION, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(helper.fillMainConsoleResponse(e.getStatus()));
        } catch (IOException e) {
            e.printStackTrace();
            log.error(LOG_SERVICE_EXCEPTION, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(helper.fillMainConsoleResponse(ExceptionError.IMAGE_FILE_IO_EXCEPTION));
        }

    }


    //@PostAuthorize("returnObject.username==authentication.name")
    @GetMapping(value = "/user/getById/{id}")
    public @ResponseBody ResponseEntity<UserResponse> findUserById(@PathVariable("id") Long id) {

        try {
            UserConsoleReport userConsoleReport = userService.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(helper.fillUserResponse(userConsoleReport,ExceptionError.SUCCESSFUL));
        }catch (ServiceException e) {
            log.error(LOG_SERVICE_EXCEPTION, e.getMessage());
            UserConsoleReport userConsoleReport=new UserConsoleReport();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(helper.fillUserResponse(userConsoleReport,e.getStatus()));
        }catch (IOException e) {
            e.printStackTrace();
            UserConsoleReport userConsoleReport=new UserConsoleReport();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(helper.fillUserResponse(userConsoleReport,ExceptionError.RETRIEVE_IMAGE_FILE_IO_EXCEPTION));
        }
    }


    //endregion

    //region <Request>

    @PostMapping(value = "request/create")
    ResponseEntity<MainConsoleResponse> createRequest(@RequestBody RequestRequest model){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(requestService.createRequest(model.getTitle(),model.getDesc(),
                    model.getMinAmount(),model.getMaxAmount(),model.getUserId(),model.getCategoryIds()));
        }
        catch (ServiceException  e) {
            log.error(LOG_SERVICE_EXCEPTION, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(helper.fillMainConsoleResponse(e.getStatus()));
        }
    }

    @PostMapping(value = "request/update")
    ResponseEntity<MainConsoleResponse> updateRequest(@RequestBody RequestRequest model){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(requestService.updateRequest(model.getId(),model.getTitle(),model.getDesc(),
                    model.getMinAmount(),model.getMaxAmount(),model.getUserId(),model.getCategoryIds()));
        }
        catch (ServiceException  e) {
            log.error(LOG_SERVICE_EXCEPTION, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(helper.fillMainConsoleResponse(e.getStatus()));
        }
    }

    @PostMapping("/request/list")
    public @ResponseBody
    ResponseEntity<RequestConsoleResponse> getAllRequestByUserId(@RequestBody RequestConsoleRequest requestConsoleRequest) {
        return ResponseEntity.status(HttpStatus.OK).body( requestService.getAllRequestByUserId(requestConsoleRequest.getUserId(),requestConsoleRequest.getPage(),requestConsoleRequest.getSize()) );
    }

    @GetMapping(value = "/request/getById/{id}")
    public @ResponseBody ResponseEntity<RequestResponse> findRequestById(@PathVariable("id") Long id) {

        try {
            RequestConsoleReport userConsoleReport = requestService.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(helper.fillRequestResponse(userConsoleReport,ExceptionError.SUCCESSFUL));
        }catch (ServiceException e) {
            log.error(LOG_SERVICE_EXCEPTION, e.getMessage());
            RequestConsoleReport requestConsoleReport=new RequestConsoleReport();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(helper.fillRequestResponse(requestConsoleReport,e.getStatus()));
        }
    }
    //endregion


    /*@GetMapping("/jwt/hello")
    public @ResponseBody  String getHello(){
        return "Hello";
    }

    @GetMapping(value = "/user/getImageById/{id}")
    public @ResponseBody ResponseEntity<MainConsoleResponse> findImageById(@PathVariable("id") Long id) {

        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.retrieveImage(id));
        }catch (ServiceException  e) {
            e.printStackTrace();
            log.error(LOG_SERVICE_EXCEPTION, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(helper.fillMainConsoleResponse(e.getStatus()));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(helper.fillMainConsoleResponse(ExceptionError.RETRIEVE_IMAGE_FILE_IO_EXCEPTION));
        }
    }
*/

    /*//region<End Point UI>

    @GetMapping("")
    public String indexPage() {
        return "index";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PreAuthorize("hasAuthority('OP_ACCESS_USER')")
    @GetMapping("/user")
    public String userPage() {
        return "user";
    }

    @PreAuthorize("hasAuthority('OP_ACCESS_ADMIN')")
    @GetMapping("/admin")
    public String adminPage() {
        return "admin";
    }

    @GetMapping(value = "users/deleteById/{id}")
    public String deleteById(@PathVariable("id") Long id) throws ServiceException, IOException {

        userService.deleteByUser(userService.findById(id));
        return "redirect:/admin";

    }

    //endregion*/
}
