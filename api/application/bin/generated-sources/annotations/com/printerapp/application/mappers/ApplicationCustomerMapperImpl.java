package com.printerapp.application.mappers;

import com.printerapp.application.results.cusomers.CustomerDocumentResult;
import com.printerapp.domain.aggregates.document.Document;
import com.printerapp.domain.aggregates.document.value_objects.DocumentId;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-03T19:01:48+0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.40.0.z20241112-1021, environment: Java 17.0.13 (Eclipse Adoptium)"
)
@Component
public class ApplicationCustomerMapperImpl implements ApplicationCustomerMapper {

    @Override
    public CustomerDocumentResult toDocumCustomerDocumentResult(Document document) {
        if ( document == null ) {
            return null;
        }

        DocumentId id = null;
        String name = null;

        id = document.getId();
        name = document.getName();

        CustomerDocumentResult customerDocumentResult = new CustomerDocumentResult( id, name );

        return customerDocumentResult;
    }
}
