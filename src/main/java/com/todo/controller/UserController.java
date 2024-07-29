package com.todo.controller;

import com.todo.config.AuthenticationRequest;
import com.todo.model.Users;
import com.todo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> createUser(@RequestBody Users user) {
        userService.createUser(user);
        Map<String, String> response = new HashMap<>();
        response.put("username", user.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/register")
    public boolean checkUsernameAvailability(@RequestParam String username) {
        return userService.isUsernameAvailable(username);
    }


    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody AuthenticationRequest request) {
        Users u = new Users();
        String token = userService.login(request);
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("username", request.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}