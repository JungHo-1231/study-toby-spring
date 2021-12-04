package com.example.studytobyspring.chapter5.service;

import com.example.studytobyspring.chapter5.config.Config;
import com.example.studytobyspring.chapter5.dao.Level;
import com.example.studytobyspring.chapter5.dao.UserDao;
import com.example.studytobyspring.chapter5.doamin.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

import static com.example.studytobyspring.chapter5.service.UserService.MIN_LOGCOUNT_FOR_SILVER;
import static com.example.studytobyspring.chapter5.service.UserService.MIN_RECCOMEND_FOR_GOLD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringJUnitConfig(Config.class)
class UserServiceTest {


    List<User> users;

    @Autowired
    UserService userService;
    @Autowired
    UserDao userDao;
    @Autowired
    DataSource dataSource;
    @Autowired
    PlatformTransactionManager transactionManager;
    @Autowired
    MailSender mailSender;


    @BeforeEach
    void setUp() {
        users = Arrays.asList(
                        new User("bumjin", "박범진", "p1", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER -1, 0 , "email.com"),
                        new User("joytouch", "강명성", "p2", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER, 0, "email.com"),
                        new User("erwins", "신승한", "p3", Level.SILVER, 0, MIN_RECCOMEND_FOR_GOLD - 1, "email.com"),
                        new User("madnite1", "이상호", "p4", Level.SILVER, 60,MIN_RECCOMEND_FOR_GOLD, "email.com"),
                        new User("green", "오민규", "p5", Level.GOLD, 100, Integer.MAX_VALUE, "email.com")
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

    @Test
    void upgradeAllOrNothing() throws Exception{
        TestUserService testUserService = new TestUserService(userDao,transactionManager, users.get(3).getId(), mailSender);

        userDao.deleteAll();

        for (User user : users) {
            userDao.add(user);
        }

        try {
            assertThatThrownBy(() -> {
                testUserService.upgradeLevels();
            }).isInstanceOf(TestUserService.TestUserServiceException.class);
        } catch (TestUserService.TestUserServiceException e) {
        }

        checkLevelUpgraded(users.get(1), false);
    }

    private void checkLevelUpgraded(User user, boolean upgraded) {
        User userUpdate = userDao.get(user.getId());
        if (upgraded) {
            assertThat(userUpdate.getLevel()).isEqualTo(user.getLevel().nextLevel());
        } else {
            assertThat(userUpdate.getLevel()).isEqualTo(user.getLevel());
        }
    }


    static class TestUserService extends UserService {
        private  String id;

        public TestUserService(UserDao userDao, PlatformTransactionManager platformTransactionManager, String id, MailSender mailSender) {
            super(userDao, platformTransactionManager, mailSender);
            this.id = id;
        }

        protected void upgradeLevel(User user) {
            if (user.getId().equals(this.id)) {
                throw new TestUserServiceException();
            }
            super.upgradeLevel(user);
        }

        class TestUserServiceException extends RuntimeException {
        }
    }



}





















































