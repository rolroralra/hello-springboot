package com.example.demo.repository.impl;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.*;

@Repository(value = "MockUserRepository")
public class MockUserRepositoryImpl implements UserRepository {
    private static List<User> users;
    private static Map<Integer, User> userMap;
    static {
        users = new ArrayList<>();
        userMap = new HashMap<>();

        User user = new User(1001, "rolroralra");
        User user2 = new User(1002, "rolroralra02");
        User user3 = new User(1003, "rolroralra03");
        User user4 = new User(1004, "rolroralra04");

        users.add(user);
        users.add(user2);
        users.add(user3);
        users.add(user4);

        users.forEach(u -> userMap.put(u.getUserId(), u));
    }

    public Map<String, String> getMessageMap() {
        Map<String, String> map = new HashMap<>();
        map.put("result", "Hello SpringBoot!");
        return map;
    }

    public List<User> getAllUsers() {
        return users;
    }

    public User insertUser(User user) throws SQLException {
        if (userMap.containsKey(user.getUserId())) {
            throw new SQLException("There exists userId " + user.getUserId());
        }

        users.add(user);
        userMap.put(user.getUserId(), user);

        return user;
    }

    public void updateUser(int userId, User user) {
        if (userMap.containsKey(userId)) {
            userMap.get(userId).setUserName(user.getUserName());
//            users.removeIf(u -> u.getUserId().equals(user.getUserId()));
//            users.add(user);
//            userMap.put(user.getUserId(), user);
        }
    }

    public void deleteUser(int userId) {
        if (userMap.containsKey(userId)) {
            users.removeIf(u -> u.getUserId().equals(userId));
            userMap.remove(userId);
        }
    }

    public User getUserByUserId(int userId) {
        return users.stream()
                .filter(user -> user.getUserId().equals(userId))
                .findAny()
                .orElse(new User(0, "no user"));
    }
}
