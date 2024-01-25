package com.myproject.mysolution.controller;

import com.myproject.mysolution.domain.Subscription;
import com.myproject.mysolution.response.ResponseHandler;
import com.myproject.mysolution.service.SubscriptionService;
import com.myproject.mysolution.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> mySubscription(@PathVariable("userId") String userId) {
        try {

            Subscription mySubscription= this.subscriptionService.getSubscription(userId);

            if (mySubscription == null) {
                throw new Exception("Subscriptions do not Exist.");
            }

            return ResponseHandler.responseBuilder(
                    HttpStatus.OK,
                    "Requested Subscription Details Loaded.",
                    mySubscription);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.responseBuilder(
                    HttpStatus.NOT_FOUND,
                    "Subscriptions do not Exist.",
                    null
            );
        }
    }

    @PostMapping
    public ResponseEntity<Object> submitSubscription(@RequestBody Subscription subscription) {
        try {
            String id = subscription.getUser_id();

            List<Subscription> subsList = this.subscriptionService.checkSubscription(id, subscription.getCompany());
            if (subsList != null && !subsList.isEmpty()) {
                return ResponseHandler.responseBuilder(
                        HttpStatus.BAD_REQUEST,
                        "Already subscribed.",
                        null
                );
            }

            if (!isValid(subscription)) {
                return ResponseHandler.responseBuilder(
                        HttpStatus.BAD_REQUEST,
                        "Need to check subscription information.",
                        null
                );
            }

            int rowCnt = this.subscriptionService.doSubscription(subscription);
            if (rowCnt != 1) {
                throw new Exception("Subscription Failed.");
            }

            return ResponseHandler.responseBuilder(
                    HttpStatus.OK,
                    "Subscription Successful.",
                    null
            );
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.responseBuilder(
                    HttpStatus.BAD_REQUEST,
                    "Subscription Failed.",
                    null
            );
        }
    }

    @PutMapping
    public ResponseEntity<Object> modify(@RequestBody Subscription subscription) {
        try {
            int rowCnt = this.subscriptionService.modify(subscription);

            if (rowCnt != 1) {
                throw new Exception("Update Failed.");
            }

            return ResponseHandler.responseBuilder(
                    HttpStatus.OK,
                    "Subscription information Modified.",
                    null
            );
        } catch (Exception e) {
            return ResponseHandler.responseBuilder(
                    HttpStatus.BAD_REQUEST,
                    "Sorry. Try again.",
                    null
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> finish(@PathVariable("id") String id) {
        try {
            int rowCnt = this.subscriptionService.remove(id);
            if (rowCnt != 1) {
                throw new Exception("Finish Failed.");
            }

            return ResponseHandler.responseBuilder(
                    HttpStatus.OK,
                    "Finish Successfully.",
                    null
            );
        } catch (Exception e) {
            return ResponseHandler.responseBuilder(
                    HttpStatus.BAD_REQUEST,
                    "Finish Failed.",
                    null
            );
        }
    }

    private boolean isValid(Subscription subscription) {
        System.out.println("subscription = " + subscription);
        return subscription != null && subscription.getUser_id() != null
                && subscription.getProduct_no() != null && !subscription.getCompany().isEmpty()
                && subscription.getMember() != null && subscription.getMonth() != null;
    }
}
