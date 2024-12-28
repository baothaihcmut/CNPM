package com.printerapp.infrastructure.persistence.mappers;

import com.printerapp.domain.aggregates.printer.value_objects.PrinterId;
import com.printerapp.domain.enums.PrinterStatus;
import com.printerapp.infrastructure.persistence.models.Printer;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-03T19:01:53+0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.40.0.z20241112-1021, environment: Java 17.0.13 (Eclipse Adoptium)"
)
@Component
public class InfraPrinterMapperImpl implements InfraPrinterMapper {

    @Override
    public Printer toPersistence(com.printerapp.domain.aggregates.printer.Printer printer) {
        if ( printer == null ) {
            return null;
        }

        Printer printer1 = new Printer();

        printer1.setId( printerIdValue( printer ) );
        printer1.setCreatedAt( printer.getCreatedAt() );
        printer1.setUpdatedAt( printer.getUpdatedAt() );
        printer1.setCode( printer.getCode() );
        printer1.setLocation( printer.getLocation() );
        printer1.setName( printer.getName() );
        printer1.setStatus( printer.getStatus() );

        return printer1;
    }

    @Override
    public com.printerapp.domain.aggregates.printer.Printer toDomain(Printer model) {
        if ( model == null ) {
            return null;
        }

        String code = null;
        String location = null;
        String name = null;
        PrinterStatus status = null;

        code = model.getCode();
        location = model.getLocation();
        name = model.getName();
        status = model.getStatus();

        com.printerapp.domain.aggregates.printer.Printer printer = new com.printerapp.domain.aggregates.printer.Printer( name, code, location, status );

        printer.setId( printerToPrinterId( model ) );
        printer.setCreatedAt( model.getCreatedAt() );
        printer.setUpdatedAt( model.getUpdatedAt() );

        return printer;
    }

    private UUID printerIdValue(com.printerapp.domain.aggregates.printer.Printer printer) {
        if ( printer == null ) {
            return null;
        }
        PrinterId id = printer.getId();
        if ( id == null ) {
            return null;
        }
        UUID value = id.getValue();
        if ( value == null ) {
            return null;
        }
        return value;
    }

    protected PrinterId printerToPrinterId(Printer printer) {
        if ( printer == null ) {
            return null;
        }

        UUID value = null;

        value = printer.getId();

        PrinterId printerId = new PrinterId( value );

        return printerId;
    }
}