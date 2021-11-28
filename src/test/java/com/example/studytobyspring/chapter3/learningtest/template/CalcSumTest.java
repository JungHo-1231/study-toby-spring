package com.example.studytobyspring.chapter3.learningtest.template;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

public class CalcSumTest {

    @Test
    void sumOfNumbers() throws Exception{
        Calculator calculator = new Calculator();
        String path = "/Users/jh/IdeaProjects/study-toby-spring/src/test/java/com/example/studytobyspring/chapter3/learningtest/template/number.txt";
        int sum = calculator.calcSum(path);
        Assertions.assertThat(sum).isEqualTo(10);
    }
}
