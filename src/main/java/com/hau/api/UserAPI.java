package com.hau.api;

import com.hau.dto.UserDTO;
import com.hau.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing User-related operations.
 */
@RestController
@RequestMapping("/api/user")

public class UserAPI {
    @Autowired
    private UserService userService;
    @PostMapping
    public UserDTO createUser(@RequestBody UserDTO newUser){
        return userService.save(newUser, "user");
    }
    
}
