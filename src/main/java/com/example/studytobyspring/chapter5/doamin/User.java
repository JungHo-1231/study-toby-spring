package com.example.studytobyspring.chapter5.doamin;

import com.example.studytobyspring.chapter5.dao.Level;
import lombok.*;

@Getter @Setter @NoArgsConstructor
@ToString @AllArgsConstructor
public class User {
    String id;
    String name;
    String password;

    Level level;
    int login;
    int recommend;
}
