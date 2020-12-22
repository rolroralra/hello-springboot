package com.example.demo.service;

import com.example.demo.model.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    List<User> getAllUsers();

    User getUserByUserId(int userId);

    User createUser(User user) throws Exception;

    void modifyUser(int userId, User user);

    void removeUser(int userId);
}
