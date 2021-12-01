package com.example.studytobyspring.chapter5.service;

import com.example.studytobyspring.chapter5.dao.Level;
import com.example.studytobyspring.chapter5.dao.UserDao;
import com.example.studytobyspring.chapter5.doamin.User;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserService {
    public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
    public static final int MIN_RECCOMEND_FOR_GOLD = 30;
    private UserDao userDao;
    private PlatformTransactionManager transactionManager;


    public UserService(UserDao userDao, PlatformTransactionManager transactionManager) {
        this.userDao = userDao;
        this.transactionManager = transactionManager;
    }

    public void upgradeLevels() throws SQLException {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            List<User> users = userDao.getAll();

            for (User user : users) {
                if (canUpgradeLevel(user)) {
                    upgradeLevel(user);
                }
            }
            transactionManager.commit(status);
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw e;
        }
    }

    protected void upgradeLevel(User user) {
        user.upgradeLevel();
        userDao.update(user);
    }

    private boolean canUpgradeLevel(User user) {
        Level currentLevel = user.getLevel();
        switch (currentLevel) {
            case BASIC:
                return (user.getLogin() >= MIN_LOGCOUNT_FOR_SILVER);
            case SILVER:
                return user.getRecommend() >= MIN_RECCOMEND_FOR_GOLD;
            case GOLD:
                return false;
            default:
                throw new IllegalArgumentException("Unknown Level: " + currentLevel);
        }
    }

    public void add(User user) {
        if (user.getLevel() == null) {
            user.setLevel(Level.BASIC);
        }
        userDao.add(user);
    }
}
