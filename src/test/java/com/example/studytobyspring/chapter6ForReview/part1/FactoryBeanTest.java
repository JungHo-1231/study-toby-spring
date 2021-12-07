package com.example.studytobyspring.chapter6ForReview.part1;

import com.example.studytobyspring.chapter6ForReview.part1.config.Config;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(Config.class)
public class FactoryBeanTest {

    @Autowired
    ApplicationContext context;

    @Test
    void getMessageFromFactoryBean() throws Exception{
        Object message = context.getBean("message");
        assertThat(message).isInstanceOf(Message.class);

        String text = ((Message) message).getText();
        assertThat(text).isEqualTo("Factory Bean");
    }


    @Test
    void getFactoryBean() throws Exception{
        Object factoryBean = context.getBean("&message");
        assertThat(factoryBean).isInstanceOf(FactoryBean.class);
    }




}
