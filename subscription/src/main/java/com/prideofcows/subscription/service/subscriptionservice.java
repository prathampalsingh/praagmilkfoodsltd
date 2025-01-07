package com.prideofcows.subscription.service;

import com.prideofcows.subscription.model.subscription;
import com.prideofcows.subscription.repository.subscriptionrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class subscriptionservice {

    @Autowired
    private subscriptionrepository subscriptionRepository;

    public subscription createSubscription(subscription subscription) {
        // Validate input
        if (!isValidFrequency(subscription.getFrequency())) {
            throw new IllegalArgumentException("Invalid frequency: " + subscription.getFrequency());
        }
        if (subscription.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        if (subscription.getStartDate().before(new Date())) {
            throw new IllegalArgumentException("Start date must be in the future");
        }
        if (subscription.getEndDate().before(subscription.getStartDate())) {
            throw new IllegalArgumentException("End date must be after start date");
        }

        return subscriptionRepository.save(subscription);
    }

    public subscription modifySubscription(Long subscriptionId, subscription subscription) {
        Optional<subscription> existingSubscription = subscriptionRepository.findById(subscriptionId);

        if (existingSubscription.isPresent()) {
            // Validate input
            if (!isValidFrequency(subscription.getFrequency())) {
                throw new IllegalArgumentException("Invalid frequency: " + subscription.getFrequency());
            }
            if (subscription.getQuantity() <= 0) {
                throw new IllegalArgumentException("Quantity must be greater than 0");
            }

            subscription updatedSubscription = existingSubscription.get();
            updatedSubscription.setFrequency(subscription.getFrequency());
            updatedSubscription.setQuantity(subscription.getQuantity());
            // Update other fields as needed

            return subscriptionRepository.save(updatedSubscription);
        } else {
            throw new SubscriptionNotFoundException("Subscription not found");
        }
    }

    public boolean cancelSubscription(Long subscriptionId) {
        Optional<subscription> existingSubscription = subscriptionRepository.findById(subscriptionId);

        if (existingSubscription.isPresent()) {
            subscription subscription = existingSubscription.get();

            // Check if cancellation fee applies
            if (isWithinCancellationWindow(subscription.getStartDate())) {
                // Apply cancellation fee logic here
                // ...
            }

            // Mark subscription as canceled
            subscription.setEndDate(new Date()); 
            subscriptionRepository.save(subscription);
            return true;
        } else {
            throw new SubscriptionNotFoundException("Subscription not found");
        }
    }

    public List<subscription> getSubscriptionsByCustomerId(Long customerId) {
        return subscriptionRepository.findAllByCustomerId(customerId); 
    }

    private boolean isValidFrequency(String frequency) {
        return "daily".equalsIgnoreCase(frequency) || "weekly".equalsIgnoreCase(frequency);
    }

    private boolean isWithinCancellationWindow(Date startDate) {
        // Calculate 7 days from the start date
        Date sevenDaysLater = new Date(startDate.getTime() + 7 * 24 * 60 * 60 * 1000); 

        // Check if current date is within the cancellation window
        return new Date().before(sevenDaysLater); 
    }

    // Custom exception for subscription not found
    public static class SubscriptionNotFoundException extends RuntimeException {
        public SubscriptionNotFoundException(String message) {
            super(message);
        }
    }
}