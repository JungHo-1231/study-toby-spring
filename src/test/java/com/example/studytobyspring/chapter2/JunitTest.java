package com.example.studytobyspring.chapter2;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;


public class JunitTest {
    static Set<JunitTest> testObject = new HashSet<>();

    @Test
    void test1() throws Exception{
        Assertions.assertThat(testObject).doesNotContain(this);
        testObject.add(this);
    }
    @Test
    void test2() throws Exception {
        Assertions.assertThat(testObject).doesNotContain(this);
        testObject.add(this);
    }

    @Test
    void test3() throws Exception {
        Assertions.assertThat(testObject).doesNotContain(this);
        testObject.add(this);
    }

}
