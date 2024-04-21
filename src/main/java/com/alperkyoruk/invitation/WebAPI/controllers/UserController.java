package com.alperkyoruk.invitation.WebAPI.controllers;

import com.alperkyoruk.invitation.business.abstracts.UserService;
import com.alperkyoruk.invitation.core.result.Result;
import com.alperkyoruk.invitation.core.security.JwtService;
import com.alperkyoruk.invitation.entities.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;


    public UserController(UserService userService){
        this.userService = userService;

    }

    @PostMapping("/registerAdmin")
    public Result registerAdmin(@RequestBody User user){
        return userService.addAdmin(user);
    }

    @PostMapping("/registerModerator")
    public Result registerModerator(@RequestBody User user){
        return userService.addModerator(user);
    }

    @GetMapping("/getUsers")
    public Result getUsers(){
        return userService.getUsers();
    }

    @GetMapping("/getUserById")
    public Result getUserById(@RequestParam int userId){
        return userService.getUserById(userId);
    }

    @GetMapping("/getUserByUsername")
    public Result getUserByUsername(@RequestParam String username){
        return userService.getByUsername(username);
    }




}
