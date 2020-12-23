package com.example.demo.service.impl;

import com.example.demo.repository.impl.MockUserRepositoryImpl;
import com.example.demo.service.HomeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service(value = "HomeService")
@Slf4j
public class HomeServiceImpl implements HomeService {
    private MockUserRepositoryImpl userRepository;

    public HomeServiceImpl(MockUserRepositoryImpl userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Map<String, String> getMessage() {
        return userRepository.getMessageMap();
    }
}
