package com.example.demo.service.impl;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "UserService")
public class UserServiceImpl implements UserService {


    private UserRepository userRepository;

    public UserServiceImpl(
            @Qualifier(value = "UserRepository2")
                    UserRepository userRepository) {
        this.userRepository = userRepository;
    }



    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public User getUserByUserId(int userId) {
        return userRepository.getUserByUserId(userId);
    }

    @Override
    public User createUser(User user) throws Exception {
        return userRepository.insertUser(user);
    }

    @Override
    public void modifyUser(int userId, User user) {
        userRepository.updateUser(userId, user);
    }

    @Override
    public void removeUser(int userId) {
        userRepository.deleteUser(userId);
    }
}
