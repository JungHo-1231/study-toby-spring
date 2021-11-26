package com.example.studytobyspring.chapter1.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SimpleConnectionMaker {
    public Connection makeNewConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.driver");
        Connection c = DriverManager.getConnection(
                "jdbc:h2:tcp://localhost/~/test", "sa", ""
        );

        return c;
    }
}
