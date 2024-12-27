package com.printerapp.application.mappers;

import com.printerapp.application.results.employee.EmployeePrinterResult;
import com.printerapp.domain.aggregates.printer.value_objects.PrinterId;
import com.printerapp.domain.aggregates.printer_employee.PrinterEmployee;
import com.printerapp.domain.aggregates.printer_employee.value_objects.PrinterEmployeeId;
import com.printerapp.domain.enums.PrinterStatus;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-03T19:01:49+0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.40.0.z20241112-1021, environment: Java 17.0.13 (Eclipse Adoptium)"
)
@Component
public class ApplicationEmployeeMapperImpl implements ApplicationEmployeeMapper {

    @Override
    public EmployeePrinterResult toEmployeePrinterResult(PrinterEmployee printerEmployee) {
        if ( printerEmployee == null ) {
            return null;
        }

        PrinterId printerId = null;
        Boolean isManager = null;
        Integer numOfTransactionDone = null;
        Integer numOfTransactionProcess = null;
        String printerCode = null;
        String printerLocation = null;
        String printerName = null;
        PrinterStatus printerStatus = null;

        printerId = printerEmployeeIdPrinterId( printerEmployee );
        isManager = printerEmployee.getIsManager();
        numOfTransactionDone = printerEmployee.getNumOfTransactionDone();
        numOfTransactionProcess = printerEmployee.getNumOfTransactionProcess();
        printerCode = printerEmployee.getPrinterCode();
        printerLocation = printerEmployee.getPrinterLocation();
        printerName = printerEmployee.getPrinterName();
        printerStatus = printerEmployee.getPrinterStatus();

        EmployeePrinterResult employeePrinterResult = new EmployeePrinterResult( printerId, isManager, numOfTransactionProcess, numOfTransactionDone, printerName, printerCode, printerLocation, printerStatus );

        return employeePrinterResult;
    }

    private PrinterId printerEmployeeIdPrinterId(PrinterEmployee printerEmployee) {
        if ( printerEmployee == null ) {
            return null;
        }
        PrinterEmployeeId id = printerEmployee.getId();
        if ( id == null ) {
            return null;
        }
        PrinterId printerId = id.getPrinterId();
        if ( printerId == null ) {
            return null;
        }
        return printerId;
    }
}
