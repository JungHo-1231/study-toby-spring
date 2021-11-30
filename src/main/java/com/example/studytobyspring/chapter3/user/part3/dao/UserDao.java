package com.example.studytobyspring.chapter3.user.part3.dao;

import com.example.studytobyspring.chapter1.user.domain.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDao {

    private JdbcTemplate jdbcTemplate;

    public UserDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void add(final User user) throws ClassNotFoundException, SQLException {
        // jdbcContext (템플릿)  , StatementStrategy (콜백)
        jdbcTemplate.update("insert into users(id, name, password) values (?,?,?)", user.getId(), user.getName(), user.getPassword());
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
                new RowMapper<User>() {
                    @Override
                    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return new User(rs.getString("id"), rs.getString("name"), rs.getString("password"));
                    }
                },
                new Object[]{id}
        );
    }

    public void deleteAll() {
        jdbcTemplate.update("delete from users");
    }

    public List<User> getAll() {
        return jdbcTemplate.query("select * from users order by id",
                (rs, rowNum) -> new User(rs.getString("id"), rs.getString("name"), rs.getString("password"))
        );
    }
}
