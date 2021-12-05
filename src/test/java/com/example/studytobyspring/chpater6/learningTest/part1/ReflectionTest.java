package com.example.studytobyspring.chpater6.learningTest.part1;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;

public class ReflectionTest {

    @Test
    void invokeTest() throws Exception{
        String name = "Spring";

        // length()
        assertThat(name.length()).isEqualTo(6);
        Method lengthMethod = String.class.getMethod("length");
        assertThat(lengthMethod.invoke(name)).isEqualTo(6);

        // chatAt()
        assertThat(name.charAt(0)).isEqualTo('S');

        Method charAtMethod = String.class.getMethod("charAt", int.class);
        assertThat(charAtMethod.invoke(name, 0)).isEqualTo('S');
    }

    @Test
    void simpleProxy() throws Exception{
        Hello hello = new HelloTarget();
        String toby = "Toby";
        assertThat(hello.sayHello(toby)).isEqualTo("Hello Toby");

        assertThat(hello.sayHi(toby)).isEqualTo("Hi Toby");

        assertThat(hello.sayThankYou(toby)).isEqualTo("Thank you Toby");

        HelloUppercase helloUppercase = new HelloUppercase(new HelloTarget());
        assertThat(helloUppercase.sayHello(toby)).isEqualTo("HELLO TOBY");
    }

    @Test
    void proxyInstanceTest() throws Exception{
        Hello proxiedHello = (Hello) Proxy.newProxyInstance(
                getClass().getClassLoader()
                , new Class[]{Hello.class}
                , new UppercaseHandler(new HelloTarget())
        );

        assertThat(proxiedHello).isNotNull();
    }

}
