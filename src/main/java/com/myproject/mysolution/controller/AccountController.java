package com.myproject.mysolution.controller;

import com.myproject.mysolution.domain.User;
import com.myproject.mysolution.response.ResponseHandler;
import com.myproject.mysolution.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final UserService userService;

    public AccountController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(@PathVariable("userId") String userId) {
        try {
            User getUser = this.userService.getUserById(userId);
            if (getUser == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Check User ID.");
            }

            return ResponseHandler.responseBuilder(
                    HttpStatus.OK, "Requested User Details Loaded.", getUser);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping
    public ResponseEntity<String> saveUser(@RequestBody User user) {
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

    @PostMapping("/check-id")
    public ResponseEntity<String> checkUserId(@RequestParam("id") String id) {
        try {
            System.out.println("id = " + id);
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

    @PutMapping
    public ResponseEntity<String> modifyUser(@RequestBody User user) {
        try {
            if (!accountCheck(user)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Check User Info.");
            }

            int rowCnt = this.userService.modifyInfo(user);
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

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> removeUser(@PathVariable("userId") String userId) {
        try {
            int rowCnt = this.userService.remove(userId);

            if (rowCnt != 1) {
                throw new Exception("Requested User Delete Failed.");
            }

            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("Requested User Delete completed.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Requested User Delete Failed.");
        }
    }

    private boolean accountCheck(User user) {
        return user != null && !user.getId().isEmpty() &&
                !user.getPassword().isEmpty() && !user.getName().isEmpty() &&
                !user.getPhone().isEmpty() && !user.getEmail().isEmpty();
    }

}
