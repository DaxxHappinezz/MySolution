package com.myproject.mysolution.auth;

import com.myproject.mysolution.config.JwtService;
import com.myproject.mysolution.domain.Role;
import com.myproject.mysolution.domain.User;
import com.myproject.mysolution.repository.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) throws Exception {
        // 1. 동일한 ID 존재 여부 확인
        User isUser = userDao.selectById(request.getId());
        System.out.println("isUser = " + isUser);

        // 2-1. 존재 O, 리턴
        if (isUser != null) {
            return null;
        }

        // 2-2. 존재 X, DB 저장, 토큰 생성 후 리턴
        User newUser = User.builder()
                .id(request.getId())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .phone(request.getPhone())
                .email(request.getEmail())
                .role(Role.USER)
                .build();

        userDao.insert(newUser);

        var jwtToken = jwtService.generateToken(newUser);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getId(), request.getPassword()));

        var user = userDao.selectById(request.getId());

        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

}
