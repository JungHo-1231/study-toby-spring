package com.example.studytobyspring.chapter5.service;

import com.example.studytobyspring.chapter5.config.Config;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(Config.class)
class UserServiceTest {
    @Autowired
    UserService userService;
    
    @Test
    void bean() throws Exception{
        Assertions.assertThat(userService).isNotNull();
    } 
}