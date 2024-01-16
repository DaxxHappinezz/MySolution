package com.myproject.mysolution.controller;

import com.myproject.mysolution.domain.User;
import com.myproject.mysolution.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String test() {
        return "loginForm";
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<String> submit(@RequestBody User user, HttpServletRequest request) {
        try {
            if (!loginCheck(user.getId(), user.getPassword()))
                throw new Exception("Login Failed.");

            User getUser = this.userService.getUserById(user.getId());

            HttpSession session =  request.getSession();
            session.setAttribute("id", getUser.getId());
            session.setAttribute("user_no", getUser.getUser_no());

            return ResponseEntity.status(HttpStatus.OK)
                    .body("Login OK.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Login Failed.");
        }
    }

    @PostMapping("/logout")
    @ResponseBody
    public ResponseEntity<String> logout(HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            session.invalidate();

            return ResponseEntity.status(HttpStatus.OK)
                    .body("Logout OK.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Logout Failed.");
        }
    }

    private boolean loginCheck(String id, String password) throws Exception {
        User user = this.userService.getUserById(id);
        return user != null && user.getPassword().equals(password);
    }
}
