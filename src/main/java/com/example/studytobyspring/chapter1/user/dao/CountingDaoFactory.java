package com.example.studytobyspring.chapter1.user.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CountingDaoFactory {

    @Bean
    public UserDao userDao() {
        return new UserDao(connectionMaker());
    }

    @Bean
    public CountingConnectionMaker connectionMaker() {
        return new CountingConnectionMaker(realConnectionMaker());
    }

    private DConnectionMaker realConnectionMaker() {
        return new DConnectionMaker();
    }
}
