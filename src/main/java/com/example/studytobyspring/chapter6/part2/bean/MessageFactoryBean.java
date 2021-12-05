package com.example.studytobyspring.chapter6.part2.bean;

import org.springframework.beans.factory.FactoryBean;

public class MessageFactoryBean implements FactoryBean<Message> {
    private String text;

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public Message getObject() throws Exception {
        return Message.newMessage(text);
    }

    @Override
    public Class<? extends Message> getObjectType() {
        return Message.class;
    }

    public boolean isSingleton() {
        return false;
    }
}
