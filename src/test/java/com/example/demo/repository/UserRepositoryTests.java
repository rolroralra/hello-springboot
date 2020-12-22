package com.example.demo.repository;

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
//@Suite.SuiteClasses({})
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testGetAllUsers() {
        List<User> users = userRepository.getAllUsers();

        assertTrue(users.size() > 0);
    }

    @Test
    public void testGetUserByUserId() {
        User user = userRepository.getUserByUserId(1001);

//        assertEquals(new User(1001, "rolroralra"), user);
        assertNotNull(user);
    }

}
