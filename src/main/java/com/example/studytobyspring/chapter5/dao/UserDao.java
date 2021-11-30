package com.example.studytobyspring.chapter5.dao;


import com.example.studytobyspring.chapter5.doamin.User;

import java.util.List;

public interface UserDao {
    void add(User user);
    User get(String id);
    List<User> getAll();
    void deleteAll();
    int getCount();
}
