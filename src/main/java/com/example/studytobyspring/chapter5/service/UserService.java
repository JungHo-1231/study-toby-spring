package com.example.studytobyspring.chapter5.service;

import com.example.studytobyspring.chapter5.dao.UserDao;

public class UserService {
    UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }
}
