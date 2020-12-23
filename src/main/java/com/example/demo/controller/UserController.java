package com.example.demo.controller;

import com.example.demo.annotation.TokenRequired;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(@Qualifier("UserService") UserService userService) {
        this.userService = userService;
    }

    @ExceptionHandler(value = {SQLException.class})
    public Map<String, String> handleSQLException(SQLException e) {
        Map<String, String> result = new HashMap<>();
        log.error(e.getMessage());

        result.put("result", "failed");
        return result;
    }

    @TokenRequired
    @GetMapping(path = "")
    public List<User> getAllUsers() throws SQLException {
        return this.userService.getAllUsers();
    }

    @TokenRequired
    @GetMapping(path = "/{userId}")
    public User getUserByUserId(@PathVariable int userId) throws SQLException {
        return this.userService.getUserByUserId(userId);
    }

    @TokenRequired
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

    @TokenRequired
    @PutMapping(path = "/{userId}")
    public Map<String, String> updateUser(@PathVariable int userId, @RequestParam String userName) throws SQLException {
        Map<String, String> map = new HashMap<>();
        this.userService.modifyUser(userId, new User(userId, userName));
        map.put("result", "success");

        return map;
    }

    @TokenRequired
    @DeleteMapping(path = "/{userId}")
    public Map<String, String> deleteUser(@PathVariable int userId) throws SQLException {
        Map<String, String> map = new HashMap<>();
        this.userService.removeUser(userId);
        map.put("result", "success");

        return map;
    }
}
