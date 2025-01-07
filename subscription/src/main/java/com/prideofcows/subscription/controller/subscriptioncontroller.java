package com.prideofcows.subscription.controller;

import com.prideofcows.subscription.model.subscription;
import com.prideofcows.subscription.service.subscriptionservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.*;
import java.util.List;

@RestController
@RequestMapping("/subscriptions")
public class subscriptioncontroller {

    @Autowired
    private subscriptionservice subscriptionService;

    @PostMapping
    public ResponseEntity<String> createSubscription(@Valid @RequestBody subscription subscription) {
        // ...
    }

    @PutMapping("/{subscriptionId}")
    public ResponseEntity<String> modifySubscription(@PathVariable Long subscriptionId,
                                                    @RequestBody subscription subscription) {
        // ...
    }

    @DeleteMapping("/{subscriptionId}")
    public ResponseEntity<String> cancelSubscription(@PathVariable Long subscriptionId) {
        // ...
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<List<subscription>> getSubscriptionsByCustomerId(@PathVariable Long customerId) {
        // ...
    }
}