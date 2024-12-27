package com.printerapp.application.mappers;

import com.printerapp.application.commands.printers.CreatePrinterCommand;
import com.printerapp.application.results.printers.PrinterResult;
import com.printerapp.application.results.printers.PrinterTransactionResult;
import com.printerapp.domain.aggregates.printer.Printer;
import com.printerapp.domain.aggregates.printer.value_objects.PrinterId;
import com.printerapp.domain.aggregates.transaction.Transaction;
import com.printerapp.domain.aggregates.transaction.value_objecs.TransactionId;
import com.printerapp.domain.aggregates.user.value_objects.UserId;
import com.printerapp.domain.aggregates.user.value_objects.UserName;
import com.printerapp.domain.enums.PrinterStatus;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-03T19:01:49+0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.40.0.z20241112-1021, environment: Java 17.0.13 (Eclipse Adoptium)"
)
@Component
public class ApplicationPrinterMapperImpl implements ApplicationPrinterMapper {

    @Override
    public Printer toDomain(CreatePrinterCommand createPrinterCommand) {
        if ( createPrinterCommand == null ) {
            return null;
        }

        String code = null;
        String location = null;
        String name = null;
        PrinterStatus status = null;

        code = createPrinterCommand.getCode();
        location = createPrinterCommand.getLocation();
        name = createPrinterCommand.getName();
        status = createPrinterCommand.getStatus();

        Printer printer = new Printer( name, code, location, status );

        return printer;
    }

    @Override
    public PrinterResult toResult(Printer printer) {
        if ( printer == null ) {
            return null;
        }

        PrinterResult.PrinterResultBuilder printerResult = PrinterResult.builder();

        printerResult.code( printer.getCode() );
        printerResult.id( printer.getId() );
        printerResult.location( printer.getLocation() );
        printerResult.name( printer.getName() );
        printerResult.status( printer.getStatus() );

        return printerResult.build();
    }

    @Override
    public PrinterTransactionResult toPrinterTransactionResult(Transaction transaction) {
        if ( transaction == null ) {
            return null;
        }

        UserId customerId = null;
        UserName customerName = null;
        UserId employeeId = null;
        UserName employeeName = null;
        TransactionId id = null;
        String name = null;
        PrinterId printerId = null;
        String transactionId = null;

        customerId = transaction.getCustomerId();
        customerName = transaction.getCustomerName();
        employeeId = transaction.getEmployeeId();
        employeeName = transaction.getEmployeeName();
        id = transaction.getId();
        name = transaction.getName();
        printerId = transaction.getPrinterId();
        transactionId = transaction.getTransactionId();

        PrinterTransactionResult printerTransactionResult = new PrinterTransactionResult( id, transactionId, customerId, customerName, employeeId, employeeName, printerId, name );

        return printerTransactionResult;
    }
}
