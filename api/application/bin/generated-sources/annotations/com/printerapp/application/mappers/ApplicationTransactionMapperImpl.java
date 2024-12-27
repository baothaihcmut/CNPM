package com.printerapp.application.mappers;

import com.printerapp.application.commands.transactions.CreateTransactionCommand;
import com.printerapp.application.results.transactions.TransactionPrinterResult;
import com.printerapp.application.results.transactions.TransactionResult;
import com.printerapp.application.results.transactions.TransactionUserResult;
import com.printerapp.domain.aggregates.customer.value_objects.PaperQuantity;
import com.printerapp.domain.aggregates.printer.Printer;
import com.printerapp.domain.aggregates.printer.value_objects.PrinterId;
import com.printerapp.domain.aggregates.transaction.Transaction;
import com.printerapp.domain.aggregates.transaction.entities.TransactionDocument;
import com.printerapp.domain.aggregates.transaction.value_objecs.DocumentDetail;
import com.printerapp.domain.aggregates.transaction.value_objecs.TransactionId;
import com.printerapp.domain.aggregates.user.User;
import com.printerapp.domain.aggregates.user.value_objects.Email;
import com.printerapp.domain.aggregates.user.value_objects.UserId;
import com.printerapp.domain.aggregates.user.value_objects.UserName;
import com.printerapp.domain.enums.PaperType;
import com.printerapp.domain.enums.PrintTransactionStatus;
import com.printerapp.domain.enums.PrinterStatus;
import com.printerapp.domain.enums.Role;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-03T19:01:49+0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.40.0.z20241112-1021, environment: Java 17.0.13 (Eclipse Adoptium)"
)
@Component
public class ApplicationTransactionMapperImpl implements ApplicationTransactionMapper {

    @Override
    public TransactionResult toResult(Transaction transaction, List<String> urls) {
        if ( transaction == null && urls == null ) {
            return null;
        }

        LocalDateTime acceptedAt = null;
        LocalDateTime createdAt = null;
        UserId customerId = null;
        UserName customerName = null;
        LocalDateTime doneAt = null;
        UserId employeeId = null;
        UserId employeeName = null;
        TransactionId id = null;
        String name = null;
        PrinterId printerId = null;
        PrintTransactionStatus status = null;
        List<TransactionDocument> transactionDocuments = null;
        String transactionId = null;
        Map<PaperType, PaperQuantity> transactionPaperQuantities = null;
        LocalDateTime updatedAt = null;
        if ( transaction != null ) {
            acceptedAt = transaction.getAcceptedAt();
            createdAt = transaction.getCreatedAt();
            customerId = transaction.getCustomerId();
            customerName = transaction.getCustomerName();
            doneAt = transaction.getDoneAt();
            employeeId = transaction.getEmployeeId();
            employeeName = userNameToUserId( transaction.getEmployeeName() );
            id = transaction.getId();
            name = transaction.getName();
            printerId = transaction.getPrinterId();
            status = transaction.getStatus();
            List<TransactionDocument> list = transaction.getTransactionDocuments();
            if ( list != null ) {
                transactionDocuments = new ArrayList<TransactionDocument>( list );
            }
            transactionId = transaction.getTransactionId();
            Map<PaperType, PaperQuantity> map = transaction.getTransactionPaperQuantities();
            if ( map != null ) {
                transactionPaperQuantities = new LinkedHashMap<PaperType, PaperQuantity>( map );
            }
            updatedAt = transaction.getUpdatedAt();
        }

        List<String> urls1 = mapUrls(urls);

        TransactionResult transactionResult = new TransactionResult( id, transactionId, customerId, customerName, employeeId, employeeName, printerId, name, status, transactionPaperQuantities, transactionDocuments, createdAt, updatedAt, acceptedAt, doneAt, urls1 );

        return transactionResult;
    }

    @Override
    public DocumentDetail toDocumentDetailDomain(CreateTransactionCommand.TransactionDocumentDetail detail) {
        if ( detail == null ) {
            return null;
        }

        Integer leftSide = null;
        Integer rightSide = null;
        Integer topSide = null;
        Integer bottomSide = null;
        Integer numOfPageOneSide = null;
        Boolean isLandscape = null;
        Integer fromPage = null;
        Boolean isOneSide = null;
        Integer numOfCopies = null;
        PaperType paperType = null;
        Integer toPage = null;

        if ( detail.getLeftSide() != null ) {
            leftSide = detail.getLeftSide();
        }
        else {
            leftSide = 10;
        }
        if ( detail.getRightSide() != null ) {
            rightSide = detail.getRightSide();
        }
        else {
            rightSide = 10;
        }
        if ( detail.getTopSide() != null ) {
            topSide = detail.getTopSide();
        }
        else {
            topSide = 10;
        }
        if ( detail.getBottomSide() != null ) {
            bottomSide = detail.getBottomSide();
        }
        else {
            bottomSide = 10;
        }
        if ( detail.getNumOfPageOneSide() != null ) {
            numOfPageOneSide = detail.getNumOfPageOneSide();
        }
        else {
            numOfPageOneSide = 1;
        }
        isLandscape = detail.getIsLandscape();
        fromPage = detail.getFromPage();
        isOneSide = detail.getIsOneSide();
        numOfCopies = detail.getNumOfCopies();
        paperType = detail.getPaperType();
        toPage = detail.getToPage();

        DocumentDetail documentDetail = new DocumentDetail( paperType, numOfCopies, isLandscape, fromPage, toPage, leftSide, rightSide, topSide, bottomSide, isOneSide, numOfPageOneSide );

        return documentDetail;
    }

    @Override
    public TransactionUserResult toUserResult(User user) {
        if ( user == null ) {
            return null;
        }

        Email email = null;
        UserId id = null;
        String phoneNumber = null;
        Role role = null;

        email = user.getEmail();
        id = user.getId();
        phoneNumber = user.getPhoneNumber();
        role = user.getRole();

        String firstName = null;
        String lastName = null;

        TransactionUserResult transactionUserResult = new TransactionUserResult( id, firstName, lastName, phoneNumber, email, role );

        return transactionUserResult;
    }

    @Override
    public TransactionPrinterResult toPrinterResult(Printer printer) {
        if ( printer == null ) {
            return null;
        }

        PrinterId id = null;
        String location = null;
        String name = null;
        PrinterStatus status = null;

        id = printer.getId();
        location = printer.getLocation();
        name = printer.getName();
        status = printer.getStatus();

        TransactionPrinterResult transactionPrinterResult = new TransactionPrinterResult( id, name, location, status );

        return transactionPrinterResult;
    }

    protected UserId userNameToUserId(UserName userName) {
        if ( userName == null ) {
            return null;
        }

        UUID value = null;

        UserId userId = new UserId( value );

        return userId;
    }
}
