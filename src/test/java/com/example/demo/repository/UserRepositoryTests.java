package com.example.demo.repository;

import com.example.demo.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;


@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
//@Suite.SuiteClasses({})
public class UserRepositoryTests {

    @Autowired
    @Qualifier("JDBCUserRepository")
    private UserRepository userRepository;

    @Test
    public void testGetAllUsers() throws SQLException {
        List<User> users = userRepository.getAllUsers();

        users.forEach(user -> log.info("{}", user));

        assertTrue(users.size() > 0);
    }

    @Test
    public void testGetUserByUserId() throws SQLException {
        int userId = 1001;
        User user = userRepository.getUserByUserId(userId);

        log.info("{}", user);

        assertEquals(user.getUserId().intValue(), userId);
    }

    @Test
    public void testInsertUser() throws SQLException {
        int userId = 1000;
        String userName = "admin";
        userRepository.insertUser(new User(userId, userName));

        User user = userRepository.getUserByUserId(userId);
        assertEquals(user, new User(userId, userName));
    }

    @Test
    public void testUpdateUser() throws SQLException {
        int userId = 1001;
        String userName = "rolroralra";
        userRepository.updateUser(userId, new User(userId, userName));

        User user = userRepository.getUserByUserId(userId);

        assertEquals(new User(userId, userName), user);
    }

    @Test
    public void testDeleteUser() throws SQLException {
        userRepository.deleteUser(1000);

        User user = userRepository.getUserByUserId(1000);
        assertEquals(0, user.getUserId().intValue());
    }

}
