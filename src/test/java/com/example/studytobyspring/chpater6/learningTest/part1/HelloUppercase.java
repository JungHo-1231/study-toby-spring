package com.example.studytobyspring.chpater6.learningTest.part1;

import java.util.Locale;

public class HelloUppercase implements Hello {
    private Hello hello;

    public HelloUppercase(Hello hello) {
        this.hello = hello;
    }

    @Override
    public String sayHello(String name) {
        return hello.sayHello(name).toUpperCase(Locale.ROOT);
    }

    @Override
    public String sayHi(String name) {
        return hello.sayHi(name).toUpperCase(Locale.ROOT);
    }

    @Override
    public String sayThankYou(String name) {
        return hello.sayThankYou(name).toUpperCase(Locale.ROOT);
    }
}
