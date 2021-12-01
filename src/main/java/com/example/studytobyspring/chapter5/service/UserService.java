package com.example.studytobyspring.chapter5.service;

import com.example.studytobyspring.chapter5.dao.Level;
import com.example.studytobyspring.chapter5.dao.UserDao;
import com.example.studytobyspring.chapter5.doamin.User;

import java.util.List;

public class UserService {
    UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    // 리팩토링 전
//    public void upgradeLevels(){
//        List<User> users = userDao.getAll();
//        for (User user : users) {
//            Boolean changed;
//            1. 현재 레벨 파악
//            2. 업그레이드 조건을 담은 로직
//            if (user.getLevel() == Level.BASIC && user.getLogin() >= 50) {
//                user.setLevel(Level.SILVER);
//                changed = true;
//            } else if (user.getLevel() == Level.SILVER && user.getRecommend() >= 30) {
//                user.setLevel(Level.GOLD);
//                changed = true;
//            } else if (user.getLevel() == Level.GOLD) {
//                changed = false;
//            } else {
//                changed =false;
//            }
//            if (changed) {
//                userDao.update(user);
//            }
//        }
//    }

    // 리팩토링 후
    public void upgradeLevels() {
        List<User> users = userDao.getAll();

        for (User user : users) {
            if (canUpgradeLevel(user)) {
                upgradeLevel(user);
            }
        }
    }

    private void upgradeLevel(User user) {
//        리팩토링 전
//        Level currentLevel = user.getLevel();
//
//        if (currentLevel == Level.BASIC) {
//            user.setLevel(Level.SILVER);
//        } else if (currentLevel == Level.SILVER) {
//            user.setLevel(Level.GOLD);
//        }
//        userDao.update(user);

//      리팩토링 후
        // user 의 업데이트 책임을 user 가 스스로 책임지게 함
        user.upgradeLevel();
        userDao.update(user);
    }

    private boolean canUpgradeLevel(User user) {
        Level currentLevel = user.getLevel();
        switch (currentLevel) {
            case BASIC:
                return (user.getLogin() >= 50);
            case SILVER:
                return user.getRecommend() >= 30;
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
