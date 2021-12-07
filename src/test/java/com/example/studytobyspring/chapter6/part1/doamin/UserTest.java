package com.example.studytobyspring.chapter6.part1.doamin;

import com.example.studytobyspring.chapter5.config.Config;
import com.example.studytobyspring.chapter5.dao.Level;
import com.example.studytobyspring.chapter5.doamin.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringJUnitConfig(Config.class)
class UserTest {
    User user;

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void upgradeLevel() throws Exception{
        Level[] values = Level.values();

        for (Level level : values) {
            if (level.nextLevel() == null) {
                continue;
            }
            user.setLevel(level);
            user.upgradeLevel();
            assertThat(user.getLevel()).isEqualTo(level.nextLevel());
        }
    }


    @Test
    void cannotUpgradeLevel() throws Exception{

        Level[] values = Level.values();

        for (Level level : values) {
            if (level.nextLevel() != null) {
                continue;
            }
            user.setLevel(level);
            assertThatThrownBy(() -> {
                user.upgradeLevel();
            }).isInstanceOf(IllegalStateException.class);
        }
    }
}
