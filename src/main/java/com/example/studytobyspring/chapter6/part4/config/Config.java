package com.example.studytobyspring.chapter6.part4.config;

import com.example.studytobyspring.chapter6.part3.bean.MessageFactoryBean;
import com.example.studytobyspring.chapter6.part3.dao.UserDao;
import com.example.studytobyspring.chapter6.part3.dao.UserDaoJdbc;
import com.example.studytobyspring.chapter6.part3.service.DummyMailSender;
import com.example.studytobyspring.chapter6.part3.service.UserServiceImpl;
import com.example.studytobyspring.chapter6.part4.TransactionAdvice;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
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
    public UserServiceImpl userService() {
        return new UserServiceImpl(userDao(), mailSender());
    }


    @Bean
    public TransactionAdvice transactionAdvice(){
        return new TransactionAdvice(transactionManager());
    }

    @Bean
    public NameMatchMethodPointcut transactionPointCut(){
        NameMatchMethodPointcut nameMatchMethodPointcut = new NameMatchMethodPointcut();
        nameMatchMethodPointcut.setMappedName("upgrade*");
        return nameMatchMethodPointcut;
    }

    @Bean
    public NameMatchMethodPointcut transactionPointcut(){
        NameMatchMethodPointcut matchMethodPointcut = new NameMatchMethodPointcut();

        matchMethodPointcut.setMappedName("upgrade*");
        matchMethodPointcut.setClassFilter(new ClassFilter() {
            @Override
            public boolean matches(Class<?> clazz) {
                return clazz.getSimpleName().contains("ServiceImpl");
            }
        });

        return matchMethodPointcut;
    }

    @Bean
    public DefaultPointcutAdvisor transactionAdvisor(){
        return new DefaultPointcutAdvisor(transactionPointCut(), transactionAdvice());
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
