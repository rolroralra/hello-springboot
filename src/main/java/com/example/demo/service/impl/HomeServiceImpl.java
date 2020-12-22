package com.example.demo.service.impl;

import com.example.demo.repository.UserRepository;
import com.example.demo.service.HomeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service(value = "HomeService")
@Slf4j
public class HomeServiceImpl implements HomeService {
    private UserRepository userRepository;

    public HomeServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Map<String, String> getMessage() {
        return userRepository.getMessageMap();
    }
}
