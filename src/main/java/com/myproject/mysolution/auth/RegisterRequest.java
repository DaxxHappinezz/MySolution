package com.myproject.mysolution.auth;

import com.myproject.mysolution.domain.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {
    private Integer user_no;
    private String id;
    private String password;
    private String name;
    private String phone;
    private String email;
    private Role role;
}
