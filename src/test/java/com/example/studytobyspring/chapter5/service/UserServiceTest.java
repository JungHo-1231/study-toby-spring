package com.example.studytobyspring.chapter5.service;

import com.example.studytobyspring.chapter5.config.Config;
import com.example.studytobyspring.chapter5.dao.Level;
import com.example.studytobyspring.chapter5.dao.UserDao;
import com.example.studytobyspring.chapter5.doamin.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(Config.class)
class UserServiceTest {

    List<User> users;

    @Autowired
    UserService userService;
    @Autowired
    UserDao userDao;

    @BeforeEach
    void setUp() {
        users = Arrays.asList(
                        new User("bumjin", "박범진", "p1", Level.BASIC, 49, 0),
                        new User("joytouch", "강명성", "p2", Level.BASIC, 50, 0),
                        new User("erwins", "신승한", "p3", Level.SILVER, 60, 29),
                        new User("madnite1", "이상호", "p4", Level.SILVER, 60,30),
                        new User("green", "오민규", "p5", Level.GOLD, 100, 100)
                );
    }

    @Test
    void bean() throws Exception{
        Assertions.assertThat(userService).isNotNull();
    }

    @Test
    void add() throws Exception{
        userDao.deleteAll();

        User userWithLevel = users.get(4);
        User userWithoutLevel = users.get(0);

        userWithoutLevel.setLevel(null);

        userService.add(userWithLevel);
        userService.add(userWithoutLevel);

        User userWithLevelRead = userDao.get(userWithLevel.getId());
        User userWithoutLevelRead = userDao.get(userWithoutLevel.getId());

        assertThat(userWithLevelRead.getLevel()).isEqualTo(userWithLevel.getLevel());
        assertThat(userWithoutLevelRead.getLevel()).isEqualTo(Level.BASIC);
    }

    @Test
    void upgradeLevels() throws Exception{
        userDao.deleteAll();

        for (User user: users) {
            userDao.add(user);
        }

        userService.upgradeLevels();

        checkLevelUpgraded(users.get(0), false);
        checkLevelUpgraded(users.get(1), true);
        checkLevelUpgraded(users.get(2), false);
        checkLevelUpgraded(users.get(3), true);
        checkLevelUpgraded(users.get(4), false);
    }

    private void checkLevelUpgraded(User user, boolean upgraded) {
        User userUpdate = userDao.get(user.getId());
        if (upgraded) {
            assertThat(userUpdate.getLevel()).isEqualTo(user.getLevel().nextLevel());
        } else {
            assertThat(userUpdate.getLevel()).isEqualTo(user.getLevel());
        }
    }

}