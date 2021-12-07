package com.example.studytobyspring.chapter6ForReview.part2;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.Test;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

public class DynamicProxyTest {
    @Test
    void proxyFactoryBean() throws Exception {
        ProxyFactoryBean pfBean = new ProxyFactoryBean();
        pfBean.setTarget(new HelloTarget());
        pfBean.addAdvice(new UppercaseAdvice());

        Hello proxiedHello = (Hello) pfBean.getObject();
        assertThat(proxiedHello.sayHello("toby")).isEqualTo("HELLO TOBY");
    }

    @Test
    void pointcutAdvisor() throws Exception{
        ProxyFactoryBean pfBean = new ProxyFactoryBean();
        pfBean.setTarget(new HelloTarget());
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedName("sayH*");

        pfBean.addAdvisor(new DefaultPointcutAdvisor(pointcut, new UppercaseAdvice()));

        Hello proxiedHello = (Hello) pfBean.getObject();
        assertThat(proxiedHello.sayHello("toby")).isEqualTo("HELLO TOBY");
        assertThat(proxiedHello.sayThankYou("toby")).isEqualTo("Thank You toby");
    }

    @Test
    void classNamePointcutAdvisor() throws Exception{
        NameMatchMethodPointcut classMethodPointcut = new NameMatchMethodPointcut();
        classMethodPointcut.setClassFilter(new ClassFilter() {
            @Override
            public boolean matches(Class<?> clazz) {
                return clazz.getSimpleName().startsWith("HelloT");
            }
        });

        classMethodPointcut.setMappedName("sayH*");

        class HelloWorld extends HelloTarget{}

        checkAdviced(new HelloTarget(), classMethodPointcut, true);
        checkAdviced(new HelloWorld(), classMethodPointcut, false);
    }

    private void checkAdviced(Object target, Pointcut pointcut, boolean adviced) {
        ProxyFactoryBean pfBean = new ProxyFactoryBean();
        pfBean.setTarget(target);
        pfBean.addAdvisors(new DefaultPointcutAdvisor(pointcut, new UppercaseAdvice()));
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
            String ret = (String) invocation.proceed();
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
