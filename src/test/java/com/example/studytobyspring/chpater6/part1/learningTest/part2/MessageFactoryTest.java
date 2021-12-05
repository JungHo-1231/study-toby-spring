package com.example.studytobyspring.chpater6.part1.learningTest.part2;

import com.example.studytobyspring.chapter6.part2.bean.Message;
import com.example.studytobyspring.chapter6.part2.config.Config;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(Config.class)
public class MessageFactoryTest {

    @Autowired
    ApplicationContext context;

    @Test
    void getFactoryBean() throws Exception{
        Object message = context.getBean("message");
        assertThat(message).isInstanceOf(Message.class);

        assertThat(((Message) message).getText()).isEqualTo("Factory Bean");
    }
}
