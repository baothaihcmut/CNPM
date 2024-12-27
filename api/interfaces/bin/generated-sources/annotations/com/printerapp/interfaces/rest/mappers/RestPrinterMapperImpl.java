package com.printerapp.interfaces.rest.mappers;

import com.printerapp.application.commands.printers.AddEmployeeToPrinterCommand;
import com.printerapp.application.commands.printers.CreatePrinterCommand;
import com.printerapp.domain.aggregates.printer.value_objects.PrinterId;
import com.printerapp.domain.aggregates.user.value_objects.UserId;
import com.printerapp.domain.enums.PrinterStatus;
import com.printerapp.interfaces.rest.dtos.printers.AddEmployeeToPrinterDTO;
import com.printerapp.interfaces.rest.dtos.printers.CreatePrinterDTO;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-03T19:01:55+0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.40.0.z20241112-1021, environment: Java 17.0.13 (Eclipse Adoptium)"
)
@Component
public class RestPrinterMapperImpl implements RestPrinterMapper {

    @Override
    public CreatePrinterCommand toCommand(CreatePrinterDTO dto) {
        if ( dto == null ) {
            return null;
        }

        String code = null;
        String location = null;
        String name = null;
        PrinterStatus status = null;

        code = dto.getCode();
        location = dto.getLocation();
        name = dto.getName();
        if ( dto.getStatus() != null ) {
            status = Enum.valueOf( PrinterStatus.class, dto.getStatus() );
        }

        CreatePrinterCommand createPrinterCommand = new CreatePrinterCommand( name, code, location, status );

        return createPrinterCommand;
    }

    @Override
    public AddEmployeeToPrinterCommand toAddEmployeeToPrinterCommand(AddEmployeeToPrinterDTO dto, UUID printerId) {
        if ( dto == null && printerId == null ) {
            return null;
        }

        AddEmployeeToPrinterCommand.AddEmployeeToPrinterCommandBuilder addEmployeeToPrinterCommand = AddEmployeeToPrinterCommand.builder();

        if ( dto != null ) {
            addEmployeeToPrinterCommand.employeeId( addEmployeeToPrinterDTOToUserId( dto ) );
            addEmployeeToPrinterCommand.isManager( dto.getIsManager() );
        }
        addEmployeeToPrinterCommand.printerId( uUIDToPrinterId( printerId ) );

        return addEmployeeToPrinterCommand.build();
    }

    protected UserId addEmployeeToPrinterDTOToUserId(AddEmployeeToPrinterDTO addEmployeeToPrinterDTO) {
        if ( addEmployeeToPrinterDTO == null ) {
            return null;
        }

        UUID value = null;

        value = addEmployeeToPrinterDTO.getEmployeeId();

        UserId userId = new UserId( value );

        return userId;
    }

    protected PrinterId uUIDToPrinterId(UUID uUID) {
        if ( uUID == null ) {
            return null;
        }

        UUID value = null;

        value = uUID;

        PrinterId printerId = new PrinterId( value );

        return printerId;
    }
}
