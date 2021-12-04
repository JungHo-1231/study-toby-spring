package com.example.studytobyspring.chapter6.service;

import com.example.studytobyspring.chapter6.dao.Level;
import com.example.studytobyspring.chapter6.dao.UserDao;
import com.example.studytobyspring.chapter6.doamin.User;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import java.util.List;

import static com.example.studytobyspring.chapter6.service.UserService1.MIN_LOGCOUNT_FOR_SILVER;
import static com.example.studytobyspring.chapter6.service.UserService1.MIN_RECCOMEND_FOR_GOLD;

public class UserServiceImpl implements UserService {

    UserDao userDao;
    MailSender mailSender;

    @Override
    public void add(User user) {
    }

    @Override
    public void upgradeLevels() {
        List<User> all = userDao.getAll();
        for (User user : all) {
            if (canUpgradeLevel(user)) {
                upgradeLevel(user);
            }
        }
    }

    private void upgradeLevel(User user) {
        user.upgradeLevel();
        userDao.update(user);
        sendUpgradeEMail(user);
    }

    private void sendUpgradeEMail(User user) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setFrom("useradmin@ksung.org");
        mailMessage.setSubject("Upgrade 안내");
        mailMessage.setText("사용자님의 등급이 " +  user.getLevel().name());

        mailSender.send(mailMessage);
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
}
