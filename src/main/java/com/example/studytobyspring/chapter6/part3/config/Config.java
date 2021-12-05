package com.example.studytobyspring.chapter6.part3.config;

import com.example.studytobyspring.chapter6.part3.bean.MessageFactoryBean;
import com.example.studytobyspring.chapter6.part3.dao.UserDao;
import com.example.studytobyspring.chapter6.part3.dao.UserDaoJdbc;
import com.example.studytobyspring.chapter6.part3.service.*;
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
    public UserService userServiceTx() {
        return new UserServiceTx(userService(), transactionManager());
    }

    @Bean
    public UserServiceImpl userService() {
        return new UserServiceImpl(userDao(), mailSender());
    }

    @Bean
    public TxProxyFactoryBean txProxyFactoryBean() {
        TxProxyFactoryBean tx = new TxProxyFactoryBean(userService(), transactionManager()
                , "upgradeLevels");
        return tx;
    }

    @Bean
    public DataSource connectionMaker() {
        SimpleDriverDataSource ds = new SimpleDriverDataSource();
        ds.setDriverClass(org.h2.Driver.class);
        ds.setUrl("jdbc:h2:tcp://localhost/~/test");
        ds.setUsername("sa");
        return ds;
    }

    @Bean(name = "message")
    public MessageFactoryBean message() {
        MessageFactoryBean factory = new MessageFactoryBean();
        factory.setText("Factory Bean");
        return factory;
    }

}
