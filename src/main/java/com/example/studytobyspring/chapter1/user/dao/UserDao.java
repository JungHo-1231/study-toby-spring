package com.example.studytobyspring.chapter1.user.dao;

import com.example.studytobyspring.chapter1.user.domain.User;

import java.sql.*;

// 초난감 Dao
public class UserDao {
    // db 연결
    // sql 실행
    // 리소스 작업 닫기
    // 예외 처리
    public void add(User user) throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.driver");
        Connection c = DriverManager.getConnection(
                "jdbc:h2:tcp://localhost/~/test", "sa", ""
        );
        PreparedStatement ps = c.prepareStatement("insert into user(id, name, password) values (?,?,?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        ps.close();
        c.close();
    }

    public User get(String id) throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.driver");
        Connection c = DriverManager.getConnection(
                "jdbc:h2:tcp://localhost/~/test", "sa", ""
        );
        PreparedStatement ps = c.prepareStatement("select * from user where id = ?");
        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();

        rs.next();

        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setName(rs.getString("password"));
        
        rs.close();
        ps.close();
        c.close();

        return user;
    }
}
