package com.example.studytobyspring.chapter3.part3;

import com.example.studytobyspring.chapter1.user.domain.User;
import com.example.studytobyspring.chapter3.user.part3.dao.DaoFactory;
import com.example.studytobyspring.chapter3.user.part3.dao.UserDao;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringJUnitConfig(DaoFactory.class)
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    void addUser() throws Exception{
        User user = new User("id", "name", "password");
        userDao.add(user);
    }

    @Test
    void getAll() throws Exception{

        userDao.deleteAll();
        List<User> user0 = userDao.getAll();
        assertThat(user0.size()).isEqualTo(0);

        User user1 = new User("gyumee", "박상철", "springno1");
        User user2 = new User("lee", "이길원", "springno2");
        User user3 = new User("bun", "박범진", "springno3");

        userDao.add(user1);
        List<User> user1List = userDao.getAll();
        assertThat(user1List.size()).isEqualTo(1);

        userDao.add(user2);
        List<User> user2List = userDao.getAll();
        assertThat(user2List.size()).isEqualTo(2);

        userDao.add(user3);
        List<User> user3List = userDao.getAll();
        assertThat(user3List.size()).isEqualTo(3);

        checkSameUser(user3, user3List.get(0));
        checkSameUser(user1, user3List.get(1));
        checkSameUser(user2, user3List.get(2));
    }

    private void checkSameUser(User user1, User user2) {
        assertThat(user1.getId()).isEqualTo(user2.getId());
        assertThat(user1.getName()).isEqualTo(user2.getName());
        assertThat(user1.getPassword()).isEqualTo(user2.getPassword());
    }
}



























