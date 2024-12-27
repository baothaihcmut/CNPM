package com.printerapp.domain.repositories;

import java.util.List;
import java.util.Optional;

import com.printerapp.domain.aggregates.printer.value_objects.PrinterId;
import com.printerapp.domain.aggregates.printer_employee.PrinterEmployee;
import com.printerapp.domain.aggregates.printer_employee.value_objects.PrinterEmployeeId;
import com.printerapp.domain.aggregates.user.value_objects.UserId;

public interface PrinterEmployeeRepository {
    void save(PrinterEmployee printerEmployee);

    Optional<PrinterEmployee> findById(PrinterEmployeeId printerEmployeeId);

    List<PrinterEmployee> findByIdEmployeeId(UserId employeeId);

    List<PrinterEmployee> findByPrinterId(PrinterId printerId);
}
