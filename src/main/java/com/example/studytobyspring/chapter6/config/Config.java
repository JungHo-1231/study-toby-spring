package com.example.studytobyspring.chapter6.config;

import com.example.studytobyspring.chapter6.dao.UserDao;
import com.example.studytobyspring.chapter6.dao.UserDaoJdbc;
import com.example.studytobyspring.chapter6.service.DummyMailSender;
import com.example.studytobyspring.chapter6.service.UserService1;
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
    public UserDao userDao(){
        return new UserDaoJdbc(connectionMaker());
    }

    @Bean
    public PlatformTransactionManager transactionManager(){
        return new DataSourceTransactionManager(connectionMaker());
    }
    @Bean
    public MailSender mailSender(){
        return new DummyMailSender();
    }

    @Bean
    public UserService1 userService(){
        return new UserService1(userDao(),  transactionManager(), mailSender());
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
