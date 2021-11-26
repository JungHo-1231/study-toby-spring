package com.example.studytobyspring.chapter1;

import com.example.studytobyspring.chapter1.user.dao.DConnectionMaker;
import com.example.studytobyspring.chapter1.user.dao.UserDao;
import com.example.studytobyspring.chapter1.user.domain.User;

import java.sql.SQLException;

public class UserDaoTest {
    // 구현 클래스의 오브젝트 간의 관계를 맺는 책임을 클라이언트에 넘겨버림.
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        DConnectionMaker connectionMaker = new DConnectionMaker();
        UserDao userDao = new UserDao(connectionMaker);
    }
}
