package com.example.studytobyspring.chapter1.user.dao;

import java.sql.Connection;
import java.sql.SQLException;

// db counting 횟수를 알고 싶다면? 관심사를 분리를 위하여 CountingConnectionMaker 를 생성
public class CountingConnectionMaker implements ConnectionMaker {

    private ConnectionMaker realConnectionMaker;
    int counter = 0;

    public CountingConnectionMaker(ConnectionMaker realConnectionMaker) {
        this.realConnectionMaker = realConnectionMaker;
    }

    @Override
    public Connection makeConnection() throws ClassNotFoundException, SQLException {
        this.counter++;
        return realConnectionMaker.makeConnection();
    }

    public int getCounter() {
        return counter;
    }
}
