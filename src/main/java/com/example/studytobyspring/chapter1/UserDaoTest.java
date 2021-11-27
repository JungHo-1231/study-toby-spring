package com.example.studytobyspring.chapter1;

import com.example.studytobyspring.chapter1.user.dao.*;
import com.example.studytobyspring.chapter1.user.domain.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

public class UserDaoTest {
    // 테스트의 책임,
    // 오브젝트 간의 관계를 맺는 책임이 생겨버림. => factory 오브젝트 생성
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        // 의존관계 주입
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDao userDao = context.getBean("userDao", UserDao.class);

        // 테스트 코드...
        User user = new User();
        user.setId("123");
        user.setPassword("456");
        user.setName("jung");
        userDao.add(user);

        User findUser = userDao.get("123");
        System.out.println("findUser = " + findUser);

        userDao.deleteUser(user.getId());
    }
}
