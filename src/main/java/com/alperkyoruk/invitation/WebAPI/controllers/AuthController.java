package com.alperkyoruk.invitation.WebAPI.controllers;

import com.alperkyoruk.invitation.business.abstracts.UserService;
import com.alperkyoruk.invitation.core.result.DataResult;
import com.alperkyoruk.invitation.core.result.ErrorDataResult;
import com.alperkyoruk.invitation.core.result.SuccessDataResult;
import com.alperkyoruk.invitation.core.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private UserService userService;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;

    public AuthController(UserService userService, AuthenticationManager authenticationManager, JwtService jwtService){
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/generateToken")
    public DataResult<String> generateToken(@RequestParam String username, @RequestParam String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        if (authentication.isAuthenticated()) {
            return new SuccessDataResult<String>(jwtService.generateToken(username), "Token generated successfully");
        }
        return new ErrorDataResult<>("Invalid username or password");
    }



}
