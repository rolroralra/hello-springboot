package com.example.demo.controller;

import com.example.demo.annotation.TokenRequired;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(@Qualifier("UserService") UserService userService) {
        this.userService = userService;
    }

    @TokenRequired
    @GetMapping(path = "")
    public List<User> getAllUsers() {
        return this.userService.getAllUsers();
    }

    @GetMapping(path = "/{userId}")
    public User getUserByUserId(@PathVariable int userId) {
        return this.userService.getUserByUserId(userId);
    }

    @PostMapping(path = "")
    public User createUser(@RequestBody User user, HttpServletResponse httpServletResponse) {
        User response = user;
        try {
            this.userService.createUser(user);
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            e.printStackTrace();
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response = new User(0, "Failed to register user.");
        }

        return response;
    }

    @PutMapping(path = "/{userId}")
    public void updateUser(@PathVariable int userId, @RequestParam String userName) {
        this.userService.modifyUser(userId, new User(userId, userName));
    }

    @DeleteMapping(path = "/{userId}")
    public void deleteUser(@PathVariable int userId) {
        this.userService.removeUser(userId);
    }
}
