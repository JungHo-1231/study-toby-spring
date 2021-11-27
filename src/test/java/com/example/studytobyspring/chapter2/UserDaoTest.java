package com.example.studytobyspring.chapter2;

import com.example.studytobyspring.chapter1.user.dao.DaoFactory;
import com.example.studytobyspring.chapter1.user.dao.UserDao;
import com.example.studytobyspring.chapter1.user.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;

import static org.assertj.core.api.Assertions.*;

public class UserDaoTest {

    @Test
    void addAndGet() throws Exception {
        // 의존관계 주입
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDao userDao = context.getBean("userDao", UserDao.class);

        userDao.deleteAll();

        assertThat(userDao.getCount()).isEqualTo(0);

        // 테스트 코드...
        User user = new User();
        user.setId("123");
        user.setPassword("456");
        user.setName("jung");
        userDao.add(user);

        assertThat(userDao.getCount()).isEqualTo(1);

        User findUser = userDao.get("123");

        assertThat(user.getId()).isEqualTo(findUser.getId());
        assertThat(user.getPassword()).isEqualTo(findUser.getPassword());

        userDao.deleteUser(user.getId());
    }

    @Test
    void count() throws Exception {
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDao userDao = context.getBean("userDao", UserDao.class);


        User user1 = new User("gyumee", "박상철", "springno1");
        User user2 = new User("lee", "이길원", "springno2");
        User user3 = new User("bun", "박범진", "springno3");

        userDao.deleteAll();
        assertThat(userDao.getCount()).isEqualTo(0);

        userDao.add(user1);
        assertThat(userDao.getCount()).isEqualTo(1);

        userDao.add(user2);
        assertThat(userDao.getCount()).isEqualTo(2);

        userDao.add(user3);
        assertThat(userDao.getCount()).isEqualTo(3);

        User findUser1 = userDao.get(user1.getId());
        assertThat(findUser1.getId()).isEqualTo(user1.getId());
        assertThat(findUser1.getName()).isEqualTo(user1.getName());
        assertThat(findUser1.getPassword()).isEqualTo(user1.getPassword());
    }

    @Test()
    void getUserFailure() throws Exception {
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDao userDao = context.getBean("userDao", UserDao.class);

        userDao.deleteAll();

        assertThatThrownBy(() -> userDao.get("unknown_id")).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    void deleteUser() throws Exception {
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
