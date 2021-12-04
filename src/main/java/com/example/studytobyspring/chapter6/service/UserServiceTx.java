package com.example.studytobyspring.chapter6.service;

import com.example.studytobyspring.chapter6.doamin.User;

public class UserServiceTx implements UserService {
    UserService userService;


    public UserServiceTx(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void add(User user) {
        userService.add(user);
    }

    @Override
    public void upgradeLevels() {
        userService.upgradeLevels();
    }
}
