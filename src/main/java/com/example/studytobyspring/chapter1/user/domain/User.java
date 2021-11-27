package com.example.studytobyspring.chapter1.user.domain;

import lombok.*;

@Getter @Setter @NoArgsConstructor
@ToString @AllArgsConstructor
public class User {
    String id;
    String name;
    String password;
}
