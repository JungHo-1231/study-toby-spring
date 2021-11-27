package com.example.studytobyspring.chapter2;

import com.example.studytobyspring.chapter1.user.dao.DaoFactory;
import com.example.studytobyspring.chapter1.user.dao.UserDao;
import com.example.studytobyspring.chapter1.user.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class UserDaoTest {

    @Test
    void addAndGet() throws Exception{
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

        Assertions.assertThat(user.getId()).isEqualTo(findUser.getId());
        Assertions.assertThat(user.getPassword()).isEqualTo(findUser.getPassword());

        userDao.deleteUser(user.getId());
    }

    @Test
    void deleteUser() throws Exception{
        // 의존관계 주입
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDao userDao = context.getBean("userDao", UserDao.class);
        User user = new User();
        user.setId("123");
        user.setPassword("456");
        user.setName("jung");
        userDao.deleteUser(user.getId());
    }
}
