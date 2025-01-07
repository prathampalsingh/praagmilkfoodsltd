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
            if (!isValidFrequency(subscription.getFrequency())) {
                throw new IllegalArgumentException("Invalid frequency: " + subscription.getFrequency());
            }
            if (subscription.getQuantity() <= 0) {
                throw new IllegalArgumentException("Quantity must be greater than 0");
            }

            subscription updatedSubscription = existingSubscription.get();
            updatedSubscription.setFrequency(subscription.getFrequency());
            updatedSubscription.setQuantity(subscription.getQuantity());

            return subscriptionRepository.save(updatedSubscription);
        } else {
            throw new SubscriptionNotFoundException("Subscription not found");
        }
    }

    public boolean cancelSubscription(Long subscriptionId) {
        Optional<subscription> existingSubscription = subscriptionRepository.findById(subscriptionId);

        if (existingSubscription.isPresent()) {
            subscription subscription = existingSubscription.get();
            if (isWithinCancellationWindow(subscription.getStartDate())) {
                double cancellationFee = subscription.getPricePerUnit() * subscription.getQuantity() * 0.1; 
                log.info("Cancellation fee of {} applied for subscription {}", cancellationFee, subscriptionId); 
            }
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
        Date sevenDaysLater = new Date(startDate.getTime() + 7 * 24 * 60 * 60 * 1000); 
        return new Date().before(sevenDaysLater); 
    }

    public static class SubscriptionNotFoundException extends RuntimeException {
        public SubscriptionNotFoundException(String message) {
            super(message);
        }
    }
}