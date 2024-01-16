package com.myproject.mysolution.controller;

import com.myproject.mysolution.domain.Subscription;
import com.myproject.mysolution.service.SubscriptionService;
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

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @GetMapping
    public ResponseEntity<String> mySubscription(HttpServletRequest request) {
        try {
            if (!loginCheck(request)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Login required.");
            }

//            HttpSession session = request.getSession();
//            Integer user_no = (Integer) session.getAttribute("user_no");
            Integer user_no = 100;

            Subscription mySubscription= this.subscriptionService.getSubscription(user_no);

            if (mySubscription == null) {
                throw new Exception("Subscriptions do not Exist.");
            }

            return ResponseEntity.status(HttpStatus.OK)
                    .body("Subscription information Loaded.");
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
//            String userId = (String) session.getAttribute("id");
//            Integer user_no = (Integer) session.getAttribute("user_no");
            String userId = "master";
            Integer user_no = 100;
            subscription.setUser_no(user_no);

            List<Subscription> subsList = this.subscriptionService.checkSubscription(user_no, subscription.getCompany());
            if (subsList != null && !subsList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Already subscribed. > userId: "+userId+", company: "+subscription.getCompany());
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

    @PatchMapping("/subscription/")
    public ResponseEntity<String> modify() {
        return ResponseEntity.status(HttpStatus.OK)
                .body("Subscription information Modified.");
    }

    private boolean loginCheck(HttpServletRequest request) {
        HttpSession session = request.getSession();
//        return session.getAttribute("id") == null;
        return true;
    }

    private boolean isValid(Subscription subscription) {
        System.out.println("subscription = " + subscription);
        return subscription != null && subscription.getUser_no() != null
                && subscription.getProduct_no() != null && !subscription.getCompany().isEmpty()
                && subscription.getMember() != null && subscription.getMonth() != null;
    }
}
