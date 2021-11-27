package com.example.studytobyspring.chapter1.user.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
public class DaoFactory {

    @Bean
    // UserDao 의 생성 책임을 맡은 팩토리 메소드 
    public UserDao userDao() throws ClassNotFoundException {
        return new UserDao(connectionMaker());
    }

    public AccountDao accountDao(){
        return new AccountDao(connectionMaker());
    }

    public MessageDao messageDao(){
        return new MessageDao(connectionMaker());
    }

//    @Bean
//    public DConnectionMaker connectionMaker() {
//        return new DConnectionMaker();
//    }

    @Bean
    public DataSource connectionMaker() {
        SimpleDriverDataSource ds = new SimpleDriverDataSource();
        ds.setDriverClass(org.h2.Driver.class);
        ds.setUrl("jdbc:h2:tcp://localhost/~/test");
        ds.setUsername("sa");
        return ds;
    }
}
