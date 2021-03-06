package com.example.studytobyspring.chapter4.user.part3.dao;

import com.example.studytobyspring.chapter1.user.domain.User;

import java.util.List;

public interface UserDao {
    void add(User user);
    User get(String id);
    List<User> getAll();
    void deleteAll();
    int getCount();
}
