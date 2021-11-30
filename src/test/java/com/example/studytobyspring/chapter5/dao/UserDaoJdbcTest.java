package com.example.studytobyspring.chapter5.dao;

import com.example.studytobyspring.chapter5.config.Config;
import com.example.studytobyspring.chapter5.dao.Level;
import com.example.studytobyspring.chapter5.dao.UserDao;
import com.example.studytobyspring.chapter5.doamin.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringJUnitConfig(Config.class)
public class UserDaoJdbcTest {

    @Autowired
    private UserDao userDao;
    @Autowired
    private DataSource dataSource;

    private User user1;
    private User user2;
    private User user3;


    @BeforeEach
    void setUp() {
        user1 = new User("gyumee", "박상철", "springno1", Level.BASIC,1,0);
        user2 = new User("lee", "이길원", "springno2", Level.SILVER, 55, 10);
        user3 = new User("bun", "박범진", "springno3", Level.GOLD, 100, 40);
    }

    @Test
    void update() throws Exception{
        userDao.deleteAll();

        userDao.add(user1); // 수정할  사용자
        userDao.add(user2); // 수정하지 않을 사용자

        user1.setName("오민규");
        user1.setPassword("spring6");
        user1.setLevel(Level.GOLD);
        user1.setRecommend(999);

        userDao.update(user1);

        User user1Update = userDao.get(user1.getId());
        checkSameUser(user1, user1Update);

        User user2same = userDao.get(user2.getId());
        checkSameUser(user2, user2same);
    }

    @Test
    void addAndGet() throws Exception{
        userDao.deleteAll();

        assertThat(userDao.getCount()).isEqualTo(0);

        userDao.add(user1);

        assertThat(userDao.getCount()).isEqualTo(1);

        final User findUser = userDao.get(user1.getId());

        assertThat(user1.getId()).isEqualTo(findUser.getId());
        assertThat(user1.getPassword()).isEqualTo(findUser.getPassword());
    }

    @Test
    void duplicateKey() throws Exception{
    }

    @Test void addUser() throws Exception{
    }

    @Test
    void getAll() throws Exception{

    }

    private void checkSameUser(User user1, User user2) {
        assertThat(user1.getId()).isEqualTo(user2.getId());
        assertThat(user1.getName()).isEqualTo(user2.getName());
        assertThat(user1.getPassword()).isEqualTo(user2.getPassword());
        assertThat(user1.getLevel()).isEqualTo(user2.getLevel());
        assertThat(user1.getLogin()).isEqualTo(user2.getLogin());
        assertThat(user1.getRecommend()).isEqualTo(user2.getRecommend());
    }
}



























