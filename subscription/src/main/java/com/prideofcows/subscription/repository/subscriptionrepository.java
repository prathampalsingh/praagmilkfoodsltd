package com.prideofcows.subscription.repository;


import com.prideofcows.subscription.model.subscription;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface subscriptionrepository extends JpaRepository<subscription, Long> {

    List<subscription> findAllByCustomerId(Long customerId);
}
