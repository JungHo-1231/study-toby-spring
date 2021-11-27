package com.example.studytobyspring.chapter1.user.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class User {
    String id;
    String name;
    String password;
}
