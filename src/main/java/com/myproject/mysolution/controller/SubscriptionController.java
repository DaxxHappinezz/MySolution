package com.myproject.mysolution.controller;

import com.myproject.mysolution.domain.Subscription;
import com.myproject.mysolution.response.ResponseHandler;
import com.myproject.mysolution.service.SubscriptionService;
import com.myproject.mysolution.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    private final UserService userService;

    public SubscriptionController(SubscriptionService subscriptionService, UserService userService) {
        this.subscriptionService = subscriptionService;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> mySubscription(@PathVariable("id") String id, HttpServletRequest request) {
        try {
            if (!loginCheck(request)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Login required.");
            }

//            HttpSession session = request.getSession();
//            String getId = (String) session.getAttribute("id");
            String getId = "test2";
            if (!getId.equals(id)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Check User ID.");
            }

            Subscription mySubscription= this.subscriptionService.getSubscription(id);

            if (mySubscription == null) {
                throw new Exception("Subscriptions do not Exist.");
            }

            return ResponseHandler.responseBuilder(HttpStatus.OK, "Requested Subscription Details Loaded.", mySubscription);

//            return ResponseEntity.status(HttpStatus.OK)
//                    .body(mySubscription);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Subscriptions do not Exist.");
        }
    }

    @PostMapping
    public ResponseEntity<String> submitSubscription(@RequestBody Subscription subscription, HttpServletRequest request) {
        try {
            if (!loginCheck(request)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Login Required.");
            }

//            HttpSession session = request.getSession();
//            String id = (String) session.getAttribute("id");
            String id = "test2";
            subscription.setUser_id(id);

            List<Subscription> subsList = this.subscriptionService.checkSubscription(id, subscription.getCompany());
            if (subsList != null && !subsList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Already subscribed. > userId: "+id+", company: "+subscription.getCompany());
            }

            if (!isValid(subscription)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Need to check subscription information.");
            }

            int rowCnt = this.subscriptionService.doSubscription(subscription);
            if (rowCnt != 1) {
                throw new Exception("Subscription Failed.");
            }

            return ResponseEntity.status(HttpStatus.OK)
                    .body("Subscription Successful.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Subscription Failed.");
        }
    }

    @PutMapping
    public ResponseEntity<String> modify(@RequestBody Subscription subscription, HttpServletRequest request) {
        try {
            System.out.println("before - subscription = " + subscription);

//            HttpSession session = request.getSession();
//            String id = (String) session.getAttribute("id");
            String id = "test";

            subscription.setUser_id(id);
            System.out.println("after  - subscription = " + subscription);

            int rowCnt = this.subscriptionService.modify(subscription);
            System.out.println("rowCnt = " + rowCnt);

            if (rowCnt != 1) {
                throw new Exception("Update Failed.");
            }

            return ResponseEntity.status(HttpStatus.OK)
                    .body("Subscription information Modified.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Sorry. Try again.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> finish(@PathVariable("id") String id) {
        try {
            int rowCnt = this.subscriptionService.remove(id);
            if (rowCnt != 1) {
                throw new Exception("Finish Failed.");
            }

            return ResponseEntity.status(HttpStatus.OK)
                    .body("Finish Successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Finish Failed.");
        }
    }

    private boolean loginCheck(HttpServletRequest request) {
        HttpSession session = request.getSession();
//        return session.getAttribute("id") == null;
        return true;
    }

    private boolean isValid(Subscription subscription) {
        System.out.println("subscription = " + subscription);
        return subscription != null && subscription.getUser_id() != null
                && subscription.getProduct_no() != null && !subscription.getCompany().isEmpty()
                && subscription.getMember() != null && subscription.getMonth() != null;
    }
}
