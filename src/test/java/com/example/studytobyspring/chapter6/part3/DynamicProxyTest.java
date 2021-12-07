package com.example.studytobyspring.chapter6.part3;

import com.example.studytobyspring.chapter6.part1.learningTest.part1.UppercaseHandler;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.Test;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

import java.lang.reflect.Proxy;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

public class DynamicProxyTest {

    @Test
    void simpleProxyTest() throws Exception {
        Hello proxiedHello = (Hello) Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[]{Hello.class},
                new UppercaseHandler(new HelloTarget())
        );
        assertThat(proxiedHello).isNotNull();
    }

    @Test
    void proxyFactoryBean() throws Exception {
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTarget(new HelloTarget());
        proxyFactoryBean.addAdvice(new UppercaseAdvice());

        Hello proxiedHello = (Hello) proxyFactoryBean.getObject();
        assertThat(proxiedHello.sayHello("toby")).isEqualTo("HELLO TOBY");
    }

    @Test
    void pointcutAdvisor() throws Exception {
        ProxyFactoryBean pfBean = new ProxyFactoryBean();
        pfBean.setTarget(new HelloTarget());

        // 메서드 이름을 비교해서 대상을 선정하는 알고리즘을 제공하는 포인트 컷 생성
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        // 이름 조건 설정, sayH 로 시작하는 모든 메소드를 선택하게 한다.
        pointcut.setMappedName("sayH*");

        // 포인트 컷과 어드바이스를 advisor 로 묶어서 한 번에 추가
        pfBean.addAdvisor(new DefaultPointcutAdvisor(pointcut, new UppercaseAdvice()));

        Hello proxiedHello = (Hello) pfBean.getObject();
        assertThat(proxiedHello.sayHi("toby")).isEqualTo("HI TOBY");
        assertThat(proxiedHello.sayHello("toby")).isEqualTo("HELLO TOBY");
        assertThat(proxiedHello.sayThankYou("toby")).isEqualTo("Thank You toby");
    }

    @Test
    void classNamePointCutAdvisor() throws Exception {
        NameMatchMethodPointcut classMethodPointCut = new NameMatchMethodPointcut();

        classMethodPointCut.setClassFilter(
                clazz -> clazz.getSimpleName().startsWith("HelloT")
        );

        classMethodPointCut.setMappedName("sayH*");


        class HelloWorld extends HelloTarget {};
        class HelloToby extends HelloTarget {};

        // 테스트
        checkAdviced(new HelloTarget(), classMethodPointCut, true);
        checkAdviced(new HelloWorld(), classMethodPointCut,  false);
        checkAdviced(new HelloToby(), classMethodPointCut, true);
    }

    private void checkAdviced(Object target, Pointcut pointcut, boolean adviced) {
        ProxyFactoryBean pfBean = new ProxyFactoryBean();
        pfBean.setTarget(target);
        pfBean.addAdvisor(new DefaultPointcutAdvisor(pointcut, new UppercaseAdvice()));
        Hello proxiedHello = (Hello) pfBean.getObject();


        if (adviced) {
            assertThat(proxiedHello.sayHi("toby")).isEqualTo("HI TOBY");
            assertThat(proxiedHello.sayHello("toby")).isEqualTo("HELLO TOBY");
        } else {
            assertThat(proxiedHello.sayThankYou("toby")).isEqualTo("Thank You toby");
        }
    }


    static class UppercaseAdvice implements MethodInterceptor {
        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            System.out.println("(로그) 함수 실행전 ..." + invocation.getMethod());
            String ret = (String) invocation.proceed();
            System.out.println("(로그) 함수 실행후 ..." + invocation.getMethod());
            return ret.toUpperCase(Locale.ROOT);
        }
    }

    static interface Hello {
        String sayHello(String name);

        String sayHi(String name);

        String sayThankYou(String name);
    }

    static class HelloTarget implements Hello {

        @Override
        public String sayHello(String name) {
            return "Hello " + name;
        }

        @Override
        public String sayHi(String name) {
            return "Hi " + name;
        }

        @Override
        public String sayThankYou(String name) {
            return "Thank You " + name;
        }
    }
}
