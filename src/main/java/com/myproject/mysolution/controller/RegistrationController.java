package com.myproject.mysolution.controller;

import com.myproject.mysolution.domain.User;
import com.myproject.mysolution.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/accounts")
    public String addAccountForm() {
        return "registrationForm";
    }

    @PostMapping("/accounts")
    @ResponseBody
    public ResponseEntity<String> submit(@RequestBody User user) {
        System.out.println("user = " + user);
        try {
            if (!accountCheck(user)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Check User Info.");
            }

            int rowCnt = this.userService.registration(user);
            if (rowCnt != 1) {
                throw new Exception("Account creation Failed.");
            }

            return ResponseEntity.status(HttpStatus.OK)
                    .body("Account created.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Account creation Failed.");
        }
    }

    private boolean accountCheck(User user) {
        return user != null && !user.getId().isEmpty() &&
                !user.getPassword().isEmpty() && !user.getName().isEmpty() &&
                !user.getPhone().isEmpty() && !user.getEmail().isEmpty();
    }

    @PostMapping("/checkId")
    @ResponseBody
    public ResponseEntity<String> checkId(@RequestParam("id") String id) {
        try {
            User user = this.userService.getUserById(id);

            if (user != null) {
                throw new Exception("User id exists.");
            }

            return ResponseEntity.status(HttpStatus.OK)
                    .body("User id available.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("User id exists.");
        }
    }

}
