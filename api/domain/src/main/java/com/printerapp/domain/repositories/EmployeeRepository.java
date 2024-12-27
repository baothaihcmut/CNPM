package com.printerapp.domain.repositories;

import java.util.List;
import java.util.Optional;

import com.printerapp.domain.aggregates.employee.Employee;
import com.printerapp.domain.aggregates.printer.value_objects.PrinterId;
import com.printerapp.domain.aggregates.user.User;
import com.printerapp.domain.aggregates.user.value_objects.UserId;

public interface EmployeeRepository {
    void save(Employee employee);

    Optional<Employee> findById(UserId userId);

    List<User> findAllEmployeeOfPrinter(PrinterId printerId);
}
