package com.example.studytobyspring.chapter1.user.dao;

public class DaoFactory {
    // UserDao 의 생성 책임을 맡은 팩토리 메소드 
    public UserDao userDao(){
        return new UserDao(connectionMaker());
    }

    public AccountDao accountDao(){
        return new AccountDao(connectionMaker());
    }
    
    public MessageDao messageDao(){
        return new MessageDao(connectionMaker());
    }

    private DConnectionMaker connectionMaker() {
        return new DConnectionMaker();
    }
}
