package com.example.studytobyspring.chapter2;

import com.example.studytobyspring.chapter1.user.dao.DaoFactory;
import com.example.studytobyspring.chapter1.user.dao.UserDao;
import com.example.studytobyspring.chapter1.user.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.*;

@SpringJUnitConfig(DaoFactory.class)
public class UserDaoJdbcTest {

    @Autowired
    private UserDao userDao;

    @Test
    void addAndGet() throws Exception {

        userDao.deleteAll();

        assertThat(userDao.getCount()).isEqualTo(0);

        // 테스트 코드...
        User user = new User("123", "456", "jung");
        userDao.add(user);

        assertThat(userDao.getCount()).isEqualTo(1);

        User findUser = userDao.get("123");

        assertThat(user.getId()).isEqualTo(findUser.getId());
        assertThat(user.getPassword()).isEqualTo(findUser.getPassword());

        userDao.deleteUser(user.getId());
    }

    @Test
    void count() throws Exception {
        userDao.deleteAll();

        User user1 = new User("gyumee", "박상철", "springno1");
        User user2 = new User("lee", "이길원", "springno2");
        User user3 = new User("bun", "박범진", "springno3");

        userDao.deleteAll();
        assertThat(userDao.getCount()).isEqualTo(0);

        userDao.add(user1);
        assertThat(userDao.getCount()).isEqualTo(1);

        userDao.add(user2);
        assertThat(userDao.getCount()).isEqualTo(2);

        userDao.add(user3);
        assertThat(userDao.getCount()).isEqualTo(3);

        User findUser1 = userDao.get(user1.getId());
        assertThat(findUser1.getId()).isEqualTo(user1.getId());
        assertThat(findUser1.getName()).isEqualTo(user1.getName());
        assertThat(findUser1.getPassword()).isEqualTo(user1.getPassword());
    }


    @Test()
    void getUserFailure() throws Exception {

        userDao.deleteAll();

        assertThatThrownBy(() -> userDao.get("unknown_id")).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    void deleteUser() throws Exception {
        // 의존관계 주입

        User user = new User("123", "456", "jung");
        userDao.deleteUser(user.getId());
    }
}
