package com.example.studytobyspring.chapter3.user.part2.dao;

import com.example.studytobyspring.chapter1.user.domain.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDao {

    private JdbcContext jdbcContext;

    public UserDao(JdbcContext jdbcContext) {
        this.jdbcContext = jdbcContext;
    }

    public void add(final User user) throws ClassNotFoundException, SQLException {
        // jdbcContext (템플릿)  , StatementStrategy (콜백)
        jdbcContext.workWithStatementStrategy(c -> {
            PreparedStatement ps = c.prepareStatement("insert into users(id, name , password) values (?,?,?)");

            ps.setString(1, user.getId());
            ps.setString(2, user.getName());
            ps.setString(3, user.getPassword());

            return ps;
        });
    }

    public void deleteAll() throws SQLException {
        // executeSql (템플릿) , sql (콜백) => 한 번 더 분리
        executeSql("delete * from users");
    }

    private void executeSql(String query) throws SQLException {
        jdbcContext.workWithStatementStrategy(c -> c.prepareStatement(query));
    }


}
