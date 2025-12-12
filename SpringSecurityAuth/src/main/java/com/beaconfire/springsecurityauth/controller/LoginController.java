package com.beaconfire.springsecurityauth.controller;

import com.beaconfire.springsecurityauth.domain.request.LoginRequest;
import com.beaconfire.springsecurityauth.domain.request.RegisterRequest;
import com.beaconfire.springsecurityauth.domain.response.LoginResponse;
import com.beaconfire.springsecurityauth.domain.response.RegisterResponse;
import com.beaconfire.springsecurityauth.entity.User;
import com.beaconfire.springsecurityauth.exception.InvalidCredentialsException;
import com.beaconfire.springsecurityauth.security.JwtProvider;
import com.beaconfire.springsecurityauth.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private AuthenticationManager authenticationManager;
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    private JwtProvider jwtProvider;

    @Autowired
    public void setJwtProvider(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }


    @PostMapping("/signup")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
        try {
            userService.registerUser(request);

            return ResponseEntity.ok(
                    RegisterResponse.builder()
                            .message("User registered successfully.")
                            .build()
            );
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    RegisterResponse.builder()
                            .message("Registration failed. Please try again.")
                            .build()
            );
        }
    }




    //User trying to log in with username and password
    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request){

        System.out.println(request.getUsername());
        System.out.println(request.getPassword());

        Authentication authentication;

        //Try to authenticate the user using the username and password
        try{

          authentication = authenticationManager.authenticate(
                  new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
          );
        } catch (AuthenticationException e){
            throw new InvalidCredentialsException("Incorrect credentials, please try again.");
        }


        User user = (User) authentication.getPrincipal();
        System.out.println(user.getUsername());


        String token = jwtProvider.createToken(user);
        System.out.println(token);

        return LoginResponse.builder()
                .message("Welcome " + user.getUsername())
                .token(token)
                .build();

    }

}
