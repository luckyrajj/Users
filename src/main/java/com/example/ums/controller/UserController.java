package com.example.ums.controller;

import com.example.ums.requestdto.LoginRequest;
import com.example.ums.requestdto.RegistrationRequest;
import com.example.ums.responsedto.UserResponse;
import com.example.ums.service.UserService;
import com.example.ums.util.ResponseBuilder;
import com.example.ums.util.ResponseStructure;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@Slf4j
public class UserController {

    private ResponseBuilder responseBuilder;
    private UserService userService;

    @PostMapping("/registers")
    public ResponseEntity<ResponseStructure<UserResponse>> registerUser(@RequestBody RegistrationRequest registrationRequest){
       UserResponse userResponse= userService.registerUser(registrationRequest);
       return responseBuilder.success(HttpStatus.OK,"user created",userResponse);
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody LoginRequest loginRequest){
        log.info("Entered the method in login controller");
       return userService.loginUser(loginRequest);
    }

    @GetMapping("/user")
    public ResponseEntity<ResponseStructure<UserResponse>> getUser(@RequestHeader("Authorization")String token){
        log.info("Entering the getUser");
        String jwtToken=token.substring(7);
        UserResponse userResponse=userService.getUserDetails(token);
        log.info("getting the user");
        return responseBuilder.success(HttpStatus.FOUND,"token object found",userResponse);
    }
}
