package com.example.studytobyspring.chapter6.part3.service;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import java.lang.reflect.Proxy;

// 생성할 오브젝트 타입을 지정할 수 있지만 범용적으로 사용하기 위해 Object 로 둔다.
public class TxProxyFactoryBean implements FactoryBean<Object> {
    Object target;
    PlatformTransactionManager transactionManager;
    String pattern;
//  fixme  Class<?> serviceInterface;
    UserService serviceInterface;

    public TxProxyFactoryBean(Object target, PlatformTransactionManager transactionManager, String pattern) {
        this.target = target;
        this.transactionManager = transactionManager;
        this.pattern = pattern;
    }

    public void setServiceInterface(UserService serviceInterface) {
        this.serviceInterface = serviceInterface;
    }

    // FactoryBean 인터페이스 구현 메서드
    @Override
    public Object getObject() throws Exception {
        TransactionHandler txHandler = new TransactionHandler(target, transactionManager, pattern);
        return Proxy.newProxyInstance((
                        getClass().getClassLoader()),
                new Class[]{UserServiceImpl.class},
                txHandler
        );
    }

    @Override
    public Class<?> getObjectType() {
        return UserService.class;
    }

    public boolean isSingleton(){
        return false;
    }
}
