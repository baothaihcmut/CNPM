package com.printerapp.interfaces.rest.mappers;

import com.printerapp.application.commands.transactions.CreateTransactionCommand;
import com.printerapp.domain.aggregates.printer.value_objects.PrinterId;
import com.printerapp.domain.enums.PaperType;
import com.printerapp.interfaces.rest.dtos.transactions.CreateTransactionDTO;
import java.util.List;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-03T19:01:55+0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.40.0.z20241112-1021, environment: Java 17.0.13 (Eclipse Adoptium)"
)
@Component
public class RestTransactionMapperImpl implements RestTransactionMapper {

    @Override
    public CreateTransactionCommand toCommand(CreateTransactionDTO dto) {
        if ( dto == null ) {
            return null;
        }

        PrinterId printerId = null;
        List<CreateTransactionCommand.OldDocument> oldDocuments = null;
        List<CreateTransactionCommand.NewDocument> newDocuments = null;
        String name = null;

        printerId = createTransactionDTOToPrinterId( dto );
        oldDocuments = mapOldDocuments( dto.getOldDocuments() );
        newDocuments = mapNewDocument( dto.getNewDocuments() );
        name = dto.getName();

        CreateTransactionCommand createTransactionCommand = new CreateTransactionCommand( name, printerId, oldDocuments, newDocuments );

        return createTransactionCommand;
    }

    @Override
    public CreateTransactionCommand.TransactionDocumentDetail toDocumentDetailCommand(CreateTransactionDTO.TransactionDocumentDetail transactionDocumentDetail) {
        if ( transactionDocumentDetail == null ) {
            return null;
        }

        Integer bottomSide = null;
        Integer fromPage = null;
        Boolean isLandscape = null;
        Boolean isOneSide = null;
        Integer leftSide = null;
        Integer numOfCopies = null;
        Integer numOfPageOneSide = null;
        PaperType paperType = null;
        Integer rightSide = null;
        Integer toPage = null;
        Integer topSide = null;

        bottomSide = transactionDocumentDetail.getBottomSide();
        fromPage = transactionDocumentDetail.getFromPage();
        isLandscape = transactionDocumentDetail.getIsLandscape();
        isOneSide = transactionDocumentDetail.getIsOneSide();
        leftSide = transactionDocumentDetail.getLeftSide();
        numOfCopies = transactionDocumentDetail.getNumOfCopies();
        numOfPageOneSide = transactionDocumentDetail.getNumOfPageOneSide();
        if ( transactionDocumentDetail.getPaperType() != null ) {
            paperType = Enum.valueOf( PaperType.class, transactionDocumentDetail.getPaperType() );
        }
        rightSide = transactionDocumentDetail.getRightSide();
        toPage = transactionDocumentDetail.getToPage();
        topSide = transactionDocumentDetail.getTopSide();

        CreateTransactionCommand.TransactionDocumentDetail transactionDocumentDetail1 = new CreateTransactionCommand.TransactionDocumentDetail( paperType, numOfCopies, isLandscape, fromPage, toPage, leftSide, rightSide, topSide, bottomSide, isOneSide, numOfPageOneSide );

        return transactionDocumentDetail1;
    }

    protected PrinterId createTransactionDTOToPrinterId(CreateTransactionDTO createTransactionDTO) {
        if ( createTransactionDTO == null ) {
            return null;
        }

        UUID value = null;

        value = createTransactionDTO.getPrinterId();

        PrinterId printerId = new PrinterId( value );

        return printerId;
    }
}
