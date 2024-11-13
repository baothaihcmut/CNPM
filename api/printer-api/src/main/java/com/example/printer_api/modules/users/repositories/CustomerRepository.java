package com.example.printer_api.modules.users.repositories;

import org.springframework.stereotype.Repository;

import com.example.printer_api.modules.users.entities.Customer;
import com.example.printer_api.shared.database.BaseRepository;

@Repository
public class CustomerRepository extends BaseRepository<Customer> {}
