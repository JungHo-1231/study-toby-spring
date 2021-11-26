package com.example.studytobyspring.chapter1;

import com.example.studytobyspring.chapter1.user.dao.DConnectionMaker;
import com.example.studytobyspring.chapter1.user.dao.DaoFactory;
import com.example.studytobyspring.chapter1.user.dao.UserDao;
import com.example.studytobyspring.chapter1.user.domain.User;

import java.sql.SQLException;

public class UserDaoTest {
    // 테스트의 책임,
    // 오브젝트 간의 관계를 맺는 책임이 생겨버림. => factory 오브젝트 생성
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserDao userDao = new DaoFactory().userDao();

        // 테스트 코드...

    }
}
