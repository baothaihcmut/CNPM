package com.printerapp.infrastructure.persistence.mappers;

import com.printerapp.domain.aggregates.printer.value_objects.PrinterId;
import com.printerapp.domain.aggregates.printer_employee.value_objects.PrinterEmployeeId;
import com.printerapp.domain.aggregates.user.value_objects.UserId;
import com.printerapp.domain.enums.PrinterStatus;
import com.printerapp.infrastructure.persistence.models.Employee;
import com.printerapp.infrastructure.persistence.models.Printer;
import com.printerapp.infrastructure.persistence.models.PrinterEmployee;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-03T19:01:53+0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.40.0.z20241112-1021, environment: Java 17.0.13 (Eclipse Adoptium)"
)
@Component
public class InfraPrinterEmployeeMapperImpl implements InfraPrinterEmployeeMapper {

    @Override
    public PrinterEmployee toPersistence(com.printerapp.domain.aggregates.printer_employee.PrinterEmployee domain) {
        if ( domain == null ) {
            return null;
        }

        PrinterEmployee printerEmployee = new PrinterEmployee();

        printerEmployee.setPrinter( printerEmployeeIdToPrinter( domain.getId() ) );
        printerEmployee.setEmployee( printerEmployeeIdToEmployee( domain.getId() ) );
        printerEmployee.setId( domain.getPersistenceId() );
        printerEmployee.setCreatedAt( domain.getCreatedAt() );
        printerEmployee.setUpdatedAt( domain.getUpdatedAt() );
        printerEmployee.setIsManager( domain.getIsManager() );
        printerEmployee.setNumOfTransactionDone( domain.getNumOfTransactionDone() );
        printerEmployee.setNumOfTransactionProcess( domain.getNumOfTransactionProcess() );

        return printerEmployee;
    }

    @Override
    public com.printerapp.domain.aggregates.printer_employee.PrinterEmployee toDomain(PrinterEmployee model) {
        if ( model == null ) {
            return null;
        }

        Boolean isManager = null;
        String printerName = null;
        String printerCode = null;
        String printerLocation = null;

        isManager = model.getIsManager();
        printerName = modelPrinterName( model );
        printerCode = modelPrinterCode( model );
        printerLocation = modelPrinterLocation( model );

        UserId employeeId = null;
        PrinterId printerId = null;
        PrinterStatus status = null;

        com.printerapp.domain.aggregates.printer_employee.PrinterEmployee printerEmployee = new com.printerapp.domain.aggregates.printer_employee.PrinterEmployee( employeeId, printerId, isManager, printerName, printerCode, printerLocation, status );

        printerEmployee.setPersistenceId( model.getId() );
        printerEmployee.setNumOfTransactionProcess( model.getNumOfTransactionProcess() );
        printerEmployee.setNumOfTransactionDone( model.getNumOfTransactionDone() );
        printerEmployee.setPrinterStatus( modelPrinterStatus( model ) );
        printerEmployee.setCreatedAt( model.getCreatedAt() );
        printerEmployee.setUpdatedAt( model.getUpdatedAt() );

        printerEmployee.setId( mapId(model) );

        return printerEmployee;
    }

    private UUID printerEmployeeIdPrinterIdValue(PrinterEmployeeId printerEmployeeId) {
        if ( printerEmployeeId == null ) {
            return null;
        }
        PrinterId printerId = printerEmployeeId.getPrinterId();
        if ( printerId == null ) {
            return null;
        }
        UUID value = printerId.getValue();
        if ( value == null ) {
            return null;
        }
        return value;
    }

    protected Printer printerEmployeeIdToPrinter(PrinterEmployeeId printerEmployeeId) {
        if ( printerEmployeeId == null ) {
            return null;
        }

        Printer printer = new Printer();

        printer.setId( printerEmployeeIdPrinterIdValue( printerEmployeeId ) );

        return printer;
    }

    private UUID printerEmployeeIdEmployeeIdValue(PrinterEmployeeId printerEmployeeId) {
        if ( printerEmployeeId == null ) {
            return null;
        }
        UserId employeeId = printerEmployeeId.getEmployeeId();
        if ( employeeId == null ) {
            return null;
        }
        UUID value = employeeId.getValue();
        if ( value == null ) {
            return null;
        }
        return value;
    }

    protected Employee printerEmployeeIdToEmployee(PrinterEmployeeId printerEmployeeId) {
        if ( printerEmployeeId == null ) {
            return null;
        }

        Employee employee = new Employee();

        employee.setId( printerEmployeeIdEmployeeIdValue( printerEmployeeId ) );
        if ( printerEmployeeId.getEmployeeId() != null ) {
            employee.setEmployeeId( map( printerEmployeeId.getEmployeeId() ).toString() );
        }

        return employee;
    }

    private String modelPrinterName(PrinterEmployee printerEmployee) {
        if ( printerEmployee == null ) {
            return null;
        }
        Printer printer = printerEmployee.getPrinter();
        if ( printer == null ) {
            return null;
        }
        String name = printer.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String modelPrinterCode(PrinterEmployee printerEmployee) {
        if ( printerEmployee == null ) {
            return null;
        }
        Printer printer = printerEmployee.getPrinter();
        if ( printer == null ) {
            return null;
        }
        String code = printer.getCode();
        if ( code == null ) {
            return null;
        }
        return code;
    }

    private String modelPrinterLocation(PrinterEmployee printerEmployee) {
        if ( printerEmployee == null ) {
            return null;
        }
        Printer printer = printerEmployee.getPrinter();
        if ( printer == null ) {
            return null;
        }
        String location = printer.getLocation();
        if ( location == null ) {
            return null;
        }
        return location;
    }

    private PrinterStatus modelPrinterStatus(PrinterEmployee printerEmployee) {
        if ( printerEmployee == null ) {
            return null;
        }
        Printer printer = printerEmployee.getPrinter();
        if ( printer == null ) {
            return null;
        }
        PrinterStatus status = printer.getStatus();
        if ( status == null ) {
            return null;
        }
        return status;
    }
}
