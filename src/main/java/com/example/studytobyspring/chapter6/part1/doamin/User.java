package com.example.studytobyspring.chapter6.part1.doamin;

import com.example.studytobyspring.chapter6.part1.dao.Level;
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
    String email;

    public void upgradeLevel(){
        Level nextLevel = this.level.nextLevel();

        if (nextLevel == null) {
            throw new IllegalStateException(this.level + " 은 업그레이드가 불가능합니다. ");
        } else {
            this.level = nextLevel;
        }
    }
}
