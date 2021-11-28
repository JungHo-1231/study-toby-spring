package com.example.studytobyspring.chapter3.learningtest.template;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class CalcSumTest {

    public static final String PATH = "/Users/jh/IdeaProjects/study-toby-spring/src/test/java/com/example/studytobyspring/chapter3/learningtest/template/number.txt";
    Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    @Test
    void sumOfNumbers() throws Exception{
        assertThat(calculator.calcSum(PATH)).isEqualTo(10);
    }

    @Test
    void multiplyOfNumbers() throws Exception{
        assertThat(calculator.calMultiply(PATH)).isEqualTo(24);
    }

    @Test
    void sumOfNumberWithLineCallback() throws Exception{
        assertThat(calculator.calcSumWithLineCallback(PATH)).isEqualTo(10);
    }
}
