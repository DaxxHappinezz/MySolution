package com.myproject.mysolution.auth;

import com.myproject.mysolution.response.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterRequest request) {
        try {
            System.out.println("register() executed.");
            AuthenticationResponse authResponse = authenticationService.register(request);

            if (authResponse == null) {
                System.out.println("if() executed from register().");
                throw new Exception("Registration failed, It's already using user ID.");
            }
            return ResponseHandler.responseBuilder(
                    HttpStatus.OK,
                    "Requested register successful.",
                    authResponse
            );
        } catch (Exception e) {
            e.printStackTrace();

            return ResponseHandler.responseBuilder(
                    HttpStatus.BAD_REQUEST,
                    "Registration failed, It's already using user ID.",
                    null
            );
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Object> authenticate(@RequestBody AuthenticationRequest request) {

        return ResponseHandler.responseBuilder(
                HttpStatus.OK,
                "Requested authenticate successful.",
                authenticationService.authenticate(request)
        );
    }
}
