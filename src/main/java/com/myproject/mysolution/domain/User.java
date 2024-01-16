package com.myproject.mysolution.domain;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class User {
    private Integer user_no;
    private String id;
    private String password;
    private String name;
    private String phone;
    private String email;

    public User(String id, String password, String name) {
        this.id = id;
        this.password = password;
        this.name = name;
    }

    public User(String id, String password, String name, String phone, String email) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }
}
