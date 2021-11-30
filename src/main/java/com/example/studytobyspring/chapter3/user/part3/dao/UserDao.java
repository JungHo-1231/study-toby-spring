package com.example.studytobyspring.chapter3.user.part3.dao;

import com.example.studytobyspring.chapter1.user.domain.User;
import org.springframework.jdbc.core.*;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDao {

    private JdbcTemplate jdbcTemplate;
    private RowMapper<User> userMapper
            = (rs, rowNum) -> new User(rs.getString("id"), rs.getString("name"), rs.getString("password"));

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
