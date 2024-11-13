package com.example.printer_api.modules.users.repositories;

import org.springframework.stereotype.Repository;

import com.example.printer_api.modules.users.entities.Employee;
import com.example.printer_api.shared.database.BaseRepository;

@Repository
public class EmployeeRepository extends BaseRepository<Employee> {}
