package com.prideofcows.subscription.controller;

import com.prideofcows.subscription.model.subscription;
import com.prideofcows.subscription.service.subscriptionservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.*;
import java.util.List;

@RestController
@RequestMapping("/subscriptions")
public class subscriptioncontroller {

    @Autowired
    private subscriptionservice subscriptionservice;

    @PostMapping
    public ResponseEntity<String> createSubscription(@Valid @RequestBody subscription subscription) {
        try {
            subscriptionservice.createSubscription(subscription);
            return ResponseEntity.ok("Subscription created successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{subscriptionId}")
    public ResponseEntity<String> modifySubscription(@PathVariable Long subscriptionId,
                                                    @RequestBody subscription updatedSubscription) {
        try {
            subscriptionservice.modifySubscription(subscriptionId, updatedSubscription);
            return ResponseEntity.ok("Subscription updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{subscriptionId}")
    public ResponseEntity<String> cancelSubscription(@PathVariable Long subscriptionId) {
        try {
            subscriptionservice.cancelSubscription(subscriptionId);
            return ResponseEntity.ok("Subscription canceled successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("getid/{customerId}")
public ResponseEntity<List<subscription>> getSubscriptionsByCustomerId(@PathVariable Long customerId) {
    if (customerId == null) {
        // Handle null customer ID case (e.g., return bad request)
        return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.ok(subscriptionservice.getSubscriptionsByCustomerId(customerId));
}
}
