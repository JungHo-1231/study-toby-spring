package com.example.studytobyspring.chapter3.user.part1.dao;

import com.example.studytobyspring.chapter1.user.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    private DataSource dataSource;

    public UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void add(final User user) throws ClassNotFoundException, SQLException {
        StatementStrategy st = c -> {
            PreparedStatement ps = c.prepareStatement("insert into users(id, name , password) values (?,?,?)");

            ps.setString(1, user.getId());
            ps.setString(2, user.getName());
            ps.setString(3, user.getPassword());

            return ps;
        };
        jdbcContextWithStatementStrategy(st);
    }

    public User get(String id) throws ClassNotFoundException, SQLException {
        Connection c = dataSource.getConnection();

        PreparedStatement ps = c.prepareStatement("select * from users where id = ?");
        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();

        User user = new User();

        if (rs.next()) {
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
        }

        rs.close();
        ps.close();
        c.close();


        if (user.getId() == null || user.getId().equals("")) throw new EmptyResultDataAccessException(1);

        return user;
    }

    public void deleteUser(String id) throws SQLException, ClassNotFoundException {
        Connection c = dataSource.getConnection();

        PreparedStatement ps = c.prepareStatement("delete from users where id = ? ");
        ps.setString(1, id);

        ps.executeUpdate();

        ps.close();
        c.close();
    }

    public void deleteAll() throws SQLException {
        StatementStrategy st = c -> c.prepareStatement("delete from users ");
        jdbcContextWithStatementStrategy(st); // ???????????? ??????, ?????? ???????????? ??????
    }

    public void jdbcContextWithStatementStrategy(StatementStrategy stmt) throws SQLException {

        Connection c = null;
        PreparedStatement ps = null;

        try {
            c = dataSource.getConnection();
            ps = stmt.makePreparedStatement(c);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                }
            }
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public int getCount() throws SQLException {
        Connection c = dataSource.getConnection();

        PreparedStatement ps = c.prepareStatement("select count(*) from users");

        ResultSet rs = ps.executeQuery();
        rs.next();
        int count = rs.getInt(1);

        rs.close();
        ps.close();
        c.close();

        return count;
    }

}
