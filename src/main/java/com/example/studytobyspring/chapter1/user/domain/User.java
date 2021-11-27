package com.example.studytobyspring.chapter1.user.domain;

import lombok.*;

@Getter @Setter
@ToString @AllArgsConstructor
@NoArgsConstructor
public class User {

    String id;
    String name;
    String password;
}
