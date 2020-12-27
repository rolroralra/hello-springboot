package com.example.demo.service.impl;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.impl.MockUserRepositoryImpl;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service(value = "UserService")
@Slf4j
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(
//            @Qualifier(value = "MockUserRepository")
//            @Qualifier(value = "JdbcUserRepository")
//            @Qualifier(value = "JdbcTemplateUserRepository")
            @Qualifier(value = "NamedParameterJdbcTemplateUserRepository")
                    UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        return userRepository.getAllUsers();
    }

    @Override
    public User getUserByUserId(int userId) throws SQLException {
        return userRepository.getUserByUserId(userId);
    }

    @Override
    public User createUser(User user) throws Exception {
        return userRepository.insertUser(user);
    }

    @Override
    public void modifyUser(int userId, User user) throws SQLException {
        userRepository.updateUser(userId, user);
    }

    @Override
    public void removeUser(int userId) throws SQLException {
        userRepository.deleteUser(userId);
    }
}
