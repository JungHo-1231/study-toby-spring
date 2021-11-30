package com.example.studytobyspring.chapter5.dao;

import com.example.studytobyspring.chapter4.DuplicateUserException;
import com.example.studytobyspring.chapter5.doamin.User;
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
            = (rs, rowNum) -> new User(
            rs.getString("id"),
            rs.getString("name"),
            rs.getString("password"),
            Level.valueOf(rs.getInt("level")),
            rs.getInt("login"),
            rs.getInt("recommend")
    );

    public UserDaoJdbc(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        this.dataSource = dataSource;
    }

    public void add(final User user) throws DuplicateUserException {
        jdbcTemplate.update("insert into users(id, name, password, level, login, recommend) values (?,?,?,?,?,?)",
                user.getId(),
                user.getName(),
                user.getPassword(),
                user.getLevel().getValue(),
                user.getLogin(),
                user.getRecommend()
        );
    }


    public int getCount() {
        return jdbcTemplate.query(con -> con.prepareStatement("select count(*) from users"),
                rs -> {
                    rs.next();
                    return rs.getInt(1);
                }
        );
    }

    @Override
    public void update(User user1) {
        jdbcTemplate.update(
                "update users set name =? , password =? , level = ? , login =? , recommend = ? where id = ?",

                user1.getName(),
                user1.getPassword(),
                user1.getLevel().getValue(),
                user1.getLogin(),
                user1.getRecommend(),
                user1.getId()

                );
    }

    public User get(String id) {
        return jdbcTemplate.queryForObject("select * from users where id = ?",
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
