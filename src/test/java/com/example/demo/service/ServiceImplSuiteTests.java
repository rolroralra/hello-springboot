package com.example.demo.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        UserServiceImplTests.class,
        SecurityServiceImplTests.class,
        HomeServiceImplTests.class
})
public class ServiceImplSuiteTests {

}
