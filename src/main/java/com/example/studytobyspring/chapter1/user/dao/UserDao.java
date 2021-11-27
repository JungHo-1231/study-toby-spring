package com.example.studytobyspring.chapter1.user.dao;

import com.example.studytobyspring.chapter1.user.domain.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.*;

public class UserDao {

    private ConnectionMaker simpleConnectionMaker;

    // 의존 관계 주입
    public UserDao(ConnectionMaker simpleConnectionMaker) {
        this.simpleConnectionMaker = simpleConnectionMaker;
    }

    // 의존 관계 검색
    public UserDao() {
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        ConnectionMaker connectionMake = context.getBean("connectionMake", ConnectionMaker.class);
        this.simpleConnectionMaker = connectionMake;
    }

    public void add(User user) throws ClassNotFoundException, SQLException {
        Connection c = getConnection();
        PreparedStatement ps = c.prepareStatement("insert into users(id, name, password) values (?,?,?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        ps.close();
        c.close();
    }

    public User get(String id) throws ClassNotFoundException, SQLException {
        Connection c = getConnection();

        PreparedStatement ps = c.prepareStatement("select * from users where id = ?");
        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();

        rs.next();

        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));
        
        rs.close();
        ps.close();
        c.close();

        return user;
    }
    public void deleteUser(String id) throws SQLException, ClassNotFoundException {
        Connection c = getConnection();

        PreparedStatement ps = c.prepareStatement("delete from users where id = ? ");
        ps.setString(1, id);

        ps.executeUpdate();

        ps.close();
        c.close();
    }

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        return simpleConnectionMaker.makeConnection();
    };


}
