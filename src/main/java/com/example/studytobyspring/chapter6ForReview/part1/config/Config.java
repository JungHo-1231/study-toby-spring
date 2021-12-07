package com.example.studytobyspring.chapter6ForReview.part1.config;

import com.example.studytobyspring.chapter6.part3.dao.UserDao;
import com.example.studytobyspring.chapter6.part3.dao.UserDaoJdbc;
import com.example.studytobyspring.chapter6.part3.service.DummyMailSender;
import com.example.studytobyspring.chapter6.part3.service.UserServiceImpl;
import com.example.studytobyspring.chapter6ForReview.part1.MessageFactoryBean;
import com.example.studytobyspring.chapter6ForReview.part1.TransactionAdvice;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
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
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
        return new DefaultAdvisorAutoProxyCreator();
    }

    @Bean
    public TransactionAdvice transactionAdvice() {
        return new TransactionAdvice(transactionManager());
    }

    @Bean
    public NameMatchMethodPointcut transactionPointcut() {
        NameMatchMethodPointcut nameMatchMethodPointcut = new NameMatchMethodPointcut();
        nameMatchMethodPointcut.setMappedName("upgrade*");
        return nameMatchMethodPointcut;
    }

    @Bean
    public DefaultPointcutAdvisor transactionAdvisor() {
        DefaultPointcutAdvisor defaultPointcutAdvisor = new DefaultPointcutAdvisor();
        defaultPointcutAdvisor.setPointcut(transactionPointcut());
        defaultPointcutAdvisor.setAdvice(transactionAdvice());
        return defaultPointcutAdvisor;
    }

    @Bean
    public MessageFactoryBean message() {
        return new MessageFactoryBean("Factory Bean");
    }

    @Bean
    public UserServiceImpl userService() {
        return userServiceImpl();
    }

    @Bean
    public UserServiceImpl userServiceImpl() {
        return new UserServiceImpl(userDao(), mailSender());
    }

    @Bean
    public UserDao userDao() {
        return new UserDaoJdbc(connectionMaker());
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(connectionMaker());
    }

    @Bean
    public DataSource connectionMaker() {
        SimpleDriverDataSource ds = new SimpleDriverDataSource();
        ds.setDriverClass(org.h2.Driver.class);
        ds.setUrl("jdbc:h2:tcp://localhost/~/test");
        ds.setUsername("sa");
        return ds;
    }

    @Bean
    public MailSender mailSender() {
        return new DummyMailSender();
    }


}
