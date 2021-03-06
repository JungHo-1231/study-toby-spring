package com.example.studytobyspring.chapter6.part1.config;

import com.example.studytobyspring.chapter6.part1.dao.UserDao;
import com.example.studytobyspring.chapter6.part1.dao.UserDaoJdbc;
import com.example.studytobyspring.chapter6.part1.service.DummyMailSender;
import com.example.studytobyspring.chapter6.part1.service.UserService;
import com.example.studytobyspring.chapter6.part1.service.UserServiceImpl;
import com.example.studytobyspring.chapter6.part1.service.UserServiceTx;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.mail.MailSender;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class Config {

    @Bean
    public UserDao userDao() {
        return new UserDaoJdbc(connectionMaker());
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(connectionMaker());
    }

    @Bean
    public MailSender mailSender() {
        return new DummyMailSender();
    }

    @Bean
    public UserService userService() {
        return new UserServiceTx(new UserServiceImpl(userDao(), mailSender()), transactionManager());
    }

    @Bean
    public DataSource connectionMaker() {
        SimpleDriverDataSource ds = new SimpleDriverDataSource();
        ds.setDriverClass(org.h2.Driver.class);
        ds.setUrl("jdbc:h2:tcp://localhost/~/test");
        ds.setUsername("sa");
        return ds;
    }

}
