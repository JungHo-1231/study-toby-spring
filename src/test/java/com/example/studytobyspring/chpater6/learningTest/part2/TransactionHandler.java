package com.example.studytobyspring.chpater6.learningTest.part2;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TransactionHandler implements InvocationHandler {

    // 부가기능을 제공할 타킷 오브젝트, 어떤  타입의 오브젝트도 가능하다.
    private Object target;
    // 트랜젹선 기능을 제공하는 데 필요한 트랜젹선 매니저
    private PlatformTransactionManager transactionManager;
    // 트랜젹션을 적용할 메서드 이름 패턴
    private String pattern;

    public TransactionHandler(Object target, PlatformTransactionManager transactionManager, String pattern) {
        this.target = target;
        this.transactionManager = transactionManager;
        this.pattern = pattern;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().startsWith(pattern)) {
            return invokeInTransaction(method, args);
        } else {
            return method.invoke(target, args);
        }
    }

    private Object invokeInTransaction(Method method, Object[] args) throws Throwable {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            Object ret = method.invoke(target, args);
            transactionManager.commit(status);
            return ret;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            throw e.getTargetException();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
