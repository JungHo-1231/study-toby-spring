package com.example.studytobyspring.chpater6.learningTest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Locale;

public class UppercaseHandler implements InvocationHandler {

    Object object;

    public UppercaseHandler(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object ret = method.invoke(proxy, args);
        if (ret instanceof String && method.getName().startsWith("say")) {
            return ((String) ret).toUpperCase(Locale.ROOT);
        } else {
            return ret;
        }
    }
}
