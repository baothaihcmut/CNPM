package com.printerapp.domain.repositories;

import java.util.Optional;

import com.printerapp.domain.aggregates.customer.Customer;
import com.printerapp.domain.aggregates.user.value_objects.UserId;

public interface CustomerRepository {
    void save(Customer customer);

    Optional<Customer> findById(UserId userId);
}
