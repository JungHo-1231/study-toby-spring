package com.example.studytobyspring.chapter1;

import com.example.studytobyspring.chapter1.user.dao.UserDao;
import com.example.studytobyspring.chapter1.user.domain.User;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserDao dao = new UserDao();

        User user = new User();
        user.setId("lime");
        user.setName("라임");
        user.setPassword("123");

        dao.add(user);

        System.out.println(user.getId()+"등록 성공");

        User user2 = dao.get(user.getId());
        System.out.println(user2.getId()+"조회 성공");
        System.out.println(user2.getName());
        System.out.println(user2.getPassword());
    }

}
