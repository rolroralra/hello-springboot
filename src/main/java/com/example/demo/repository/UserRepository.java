package com.example.demo.repository;

import com.example.demo.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserRepository {
    List<User> getAllUsers() throws SQLException;

    User getUserByUserId(int userId) throws SQLException;

    User insertUser(User user) throws SQLException;

    void updateUser(int userId, User user) throws SQLException;

    void deleteUser(int userId) throws SQLException;
}
