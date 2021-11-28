package com.example.studytobyspring.chapter3.learningtest.template;

public interface LineCallback<T> {
    T doSomethingWithLine(String line, T value);
}
