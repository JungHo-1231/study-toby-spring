package com.example.studytobyspring.chapter4.user.part3.dao;

import com.example.studytobyspring.chapter1.user.domain.User;
import com.example.studytobyspring.chapter4.DuplicateUserException;
import org.h2.api.ErrorCode;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class UserDaoJdbc implements UserDao {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private RowMapper<User> userMapper
            = (rs, rowNum) -> new User(rs.getString("id"), rs.getString("name"), rs.getString("password"));

    public UserDaoJdbc(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        this.dataSource = dataSource;
    }

//    public void add(final User user) throws DuplicateUserIdException {
//        try (
//                Connection c = dataSource.getConnection();
//                final PreparedStatement ps = c.prepareStatement("insert into users (id, name, password) values (?,?,?)")
//
//        ) {
//            ps.setString(1, user.getId());
//            ps.setString(2, user.getName());
//            ps.setString(3, user.getPassword());
//
//            ps.executeUpdate();
//        } catch (SQLException e) {
//            if (e.getErrorCode() == ErrorCode.DUPLICATE_COLUMN_NAME_1) {
//                throw new DuplicateUserIdException(e); // 예외 전환
//            } else {
//                throw new RuntimeException(e);
//            }
//        }
//    }


    public void add(final User user) throws DuplicateUserException {
        try (
                Connection c = dataSource.getConnection();
                final PreparedStatement ps = c.prepareStatement("insert into users (id, name, password) values (?,?,?)")

        ) {
            ps.setString(1, user.getId());
            ps.setString(2, user.getName());
            ps.setString(3, user.getPassword());

            ps.executeUpdate();
        } catch (SQLException e) {
            if (e.getErrorCode() == ErrorCode.DUPLICATE_COLUMN_NAME_1) {
                throw new DuplicateUserException(e); // 예외 전환
            } else {
                throw new RuntimeException(e);
            }
        }
    }


    public int getCount() {
        return jdbcTemplate.query(con -> con.prepareStatement("select count(*) from users"),
                rs -> {
                    rs.next();
                    return rs.getInt(1);
                }
        );
    }

    public User get(String id) {
        return jdbcTemplate.queryForObject("select * from users  where id = ?",
                userMapper,
                new Object[]{id}
        );
    }

    public void deleteAll() {
        jdbcTemplate.update("delete from users");
    }

    public List<User> getAll() {
        return jdbcTemplate.query("select * from users order by id", userMapper);
    }
}
