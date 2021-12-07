package com.example.studytobyspring.chapter6ForReview.part1;

import com.example.studytobyspring.chapter6.part3.dao.Level;
import com.example.studytobyspring.chapter6.part3.dao.UserDao;
import com.example.studytobyspring.chapter6.part3.doamin.User;
import com.example.studytobyspring.chapter6.part3.service.UserService;
import com.example.studytobyspring.chapter6.part3.service.UserServiceImpl;
import com.example.studytobyspring.chapter6ForReview.part1.config.Config;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.MailSender;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

import static com.example.studytobyspring.chapter6.part1.service.UserServiceImpl.MIN_LOGCOUNT_FOR_SILVER;
import static com.example.studytobyspring.chapter6.part1.service.UserServiceImpl.MIN_RECCOMEND_FOR_GOLD;
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
    @Autowired
    ApplicationContext context;


    @BeforeEach
    void setUp() {
        users = Arrays.asList(
                new User("bumjin", "박범진", "p1", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER - 1, 0, "email.com"),
                new User("joytouch", "강명성", "p2", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER, 0, "email.com"),
                new User("erwins", "신승한", "p3", Level.SILVER, 0, MIN_RECCOMEND_FOR_GOLD - 1, "email.com"),
                new User("madnite1", "이상호", "p4", Level.SILVER, 60, MIN_RECCOMEND_FOR_GOLD, "email.com"),
                new User("green", "오민규", "p5", Level.GOLD, 100, Integer.MAX_VALUE, "email.com")
        );
    }




    @Test
    @DirtiesContext
    void upgradeAllOrNothing() throws Exception {


        TestUserService testUserService = new TestUserService(userDao, transactionManager, users.get(3).getId(), mailSender);

        ProxyFactoryBean txProxyFactoryBean = context.getBean("&userService", ProxyFactoryBean.class);
        txProxyFactoryBean.setTarget(testUserService);
        UserService txUserService = (UserService) txProxyFactoryBean.getObject();


        userDao.deleteAll();

        for (User user : users) {
            userDao.add(user);
        }

        try {
            assertThatThrownBy(() -> {
//                txUserService.upgradeLevels();
                txUserService.upgradeLevels();
            }).isInstanceOf(TestUserServiceException.class);
        } catch (TestUserServiceException e) {
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


    static class TestUserService extends UserServiceImpl {
        private String id;
        private UserDao userDao;

        private TestUserService(UserDao userDao, PlatformTransactionManager platformTransactionManager, String id, MailSender mailSender) {
            super(userDao, mailSender);
            this.id = id;
            this.userDao = userDao;
        }

        protected void upgradeLevel(User user) {
            if (user.getId().equals(this.id)) throw new TestUserServiceException();
            super.upgradeLevel(user);
        }
    }

    static class TestUserServiceException extends RuntimeException {
    }
}





















































