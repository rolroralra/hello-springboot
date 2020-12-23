package com.example.demo.service;

import com.example.demo.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserService {
    List<User> getAllUsers() throws SQLException;

    User getUserByUserId(int userId) throws SQLException;

    User createUser(User user) throws Exception;

    void modifyUser(int userId, User user) throws SQLException;

    void removeUser(int userId) throws SQLException;
}
