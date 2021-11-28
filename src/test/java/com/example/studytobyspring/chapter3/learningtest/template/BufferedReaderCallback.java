package com.example.studytobyspring.chapter3.learningtest.template;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface BufferedReaderCallback {
    Integer doSomethingWithReader(BufferedReader br) throws IOException;
}
