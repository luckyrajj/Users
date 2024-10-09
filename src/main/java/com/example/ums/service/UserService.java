package com.example.ums.service;

import com.example.ums.entity.User;
import com.example.ums.mapper.UserMapper;
import com.example.ums.repository.UserRepository;
import com.example.ums.requestdto.LoginRequest;
import com.example.ums.requestdto.RegistrationRequest;
import com.example.ums.responsedto.UserResponse;
import com.example.ums.security.JwtService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;

    public UserResponse registerUser(RegistrationRequest registrationRequest) {
        User user=userMapper.mapToUser(registrationRequest,new User());
         userRepository.save(user);
         return userMapper.mapToUserResponse(user);
    }

    public String loginUser(LoginRequest loginRequest) {
        log.info("Login user method of service");
        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword());
        log.info("UsernamePasswordAuthentication token created");
        Authentication authentication=authenticationManager.authenticate(authenticationToken);
        log.info("token being authenticated by the authentication manager");
        if(authentication.isAuthenticated()){
            log.info("checked authentication of the token");
            return userRepository.findByEmail(loginRequest.getEmail())
                    .map(user->jwtService.createToken(user.getUserId(),user.getEmail(),user.getRole()))
                    .orElseThrow(()-> new UsernameNotFoundException("Invalid Credentials"));
        }
        return null;
    }


    public UserResponse getUserDetails(String token) {
        log.info("entering the getUsrDetails");
        String username = jwtService.extractEmail(token);
        log.info("fetching the email");
        // Fetch user details from your user repository using the username
        return userRepository.findByEmail(username).map(user->{
          return  userMapper.mapToUserResponse(user);
        }).orElseThrow();

    }
}
