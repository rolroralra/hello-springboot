package com.example.demo.service;

import com.example.demo.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceImplTests {

    @Autowired
    private UserService userService;

    @Test
    public void testAllUsers() {
        List<User> users = userService.getAllUsers();
        assertEquals(4, users.size());
    }

    @Test
    public void testSingleUser() {
        User user = userService.getUserByUserId(1001);
        assertTrue(user.getUserName().contains("rolroralra"));
    }

    @Test
    public void testRegisterUser() {
        User user = new User(1000, "guest");

        assertNotNull(user);
    }

    @Test
    public void testRegisterUserThrowException() {
        User user = new User(1001, "guest");
        Exception exception = assertThrows(Exception.class, () -> userService.createUser(user));

        assertNotNull(exception.getMessage());
    }

}
