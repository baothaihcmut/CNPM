package com.printerapp.infrastructure.persistence.mappers;

import com.printerapp.domain.aggregates.document.value_objects.DocumentId;
import com.printerapp.domain.aggregates.document.value_objects.ObjectLink;
import com.printerapp.domain.aggregates.printer.value_objects.PrinterId;
import com.printerapp.domain.aggregates.transaction.Transaction;
import com.printerapp.domain.aggregates.transaction.value_objecs.DocumentDetail;
import com.printerapp.domain.aggregates.transaction.value_objecs.TransactionId;
import com.printerapp.domain.aggregates.user.value_objects.UserId;
import com.printerapp.domain.aggregates.user.value_objects.UserName;
import com.printerapp.domain.enums.PaperType;
import com.printerapp.infrastructure.persistence.models.Customer;
import com.printerapp.infrastructure.persistence.models.Document;
import com.printerapp.infrastructure.persistence.models.Employee;
import com.printerapp.infrastructure.persistence.models.PrintTransaction;
import com.printerapp.infrastructure.persistence.models.Printer;
import com.printerapp.infrastructure.persistence.models.TransactionDocument;
import com.printerapp.infrastructure.persistence.models.User;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-03T19:01:53+0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.40.0.z20241112-1021, environment: Java 17.0.13 (Eclipse Adoptium)"
)
@Component
public class InfraTransactionMapperImpl implements InfraTransactionMapper {

    @Override
    public PrintTransaction toPersistence(Transaction transaction) {
        if ( transaction == null ) {
            return null;
        }

        PrintTransaction printTransaction = new PrintTransaction();

        printTransaction.setCustomer( userIdToCustomer( transaction.getCustomerId() ) );
        printTransaction.setPrinter( printerIdToPrinter( transaction.getPrinterId() ) );
        printTransaction.setDocuments( mapDocuments( transaction.getTransactionDocuments() ) );
        printTransaction.setId( transactionIdValue( transaction ) );
        printTransaction.setCreatedAt( transaction.getCreatedAt() );
        printTransaction.setUpdatedAt( transaction.getUpdatedAt() );
        printTransaction.setAcceptedAt( transaction.getAcceptedAt() );
        printTransaction.setDoneAt( transaction.getDoneAt() );
        printTransaction.setName( transaction.getName() );
        printTransaction.setStatus( transaction.getStatus() );
        printTransaction.setTransactionId( transaction.getTransactionId() );
        printTransaction.setEmployee( transactionToEmployee( transaction ) );

        printTransaction.setTotalNumOfPaperA3( mapPaperQuantity(transaction.getTransactionPaperQuantities(),PaperType.A3) );
        printTransaction.setTotalNumOfPaperA4( mapPaperQuantity(transaction.getTransactionPaperQuantities(),PaperType.A4) );
        printTransaction.setTotalNumOfPaperA5( mapPaperQuantity(transaction.getTransactionPaperQuantities(),PaperType.A5) );

        return printTransaction;
    }

    @Override
    public Transaction toDomain(PrintTransaction printTransaction) {
        if ( printTransaction == null ) {
            return null;
        }

        UserId customerId = null;
        UserName customerName = null;
        PrinterId printerId = null;
        String name = null;

        customerId = customerToUserId( printTransaction.getCustomer() );
        customerName = customerToUserName( printTransaction.getCustomer() );
        printerId = printerToPrinterId( printTransaction.getPrinter() );
        name = printTransaction.getName();

        Transaction transaction = new Transaction( customerId, printerId, name, customerName );

        transaction.setId( printTransactionToTransactionId( printTransaction ) );
        transaction.setEmployeeId( employeeToUserId( printTransaction.getEmployee() ) );
        transaction.setEmployeeName( employeeToUserName( printTransaction.getEmployee() ) );
        transaction.setCreatedAt( printTransaction.getCreatedAt() );
        transaction.setUpdatedAt( printTransaction.getUpdatedAt() );
        transaction.setAcceptedAt( printTransaction.getAcceptedAt() );
        transaction.setDoneAt( printTransaction.getDoneAt() );
        transaction.setStatus( printTransaction.getStatus() );
        transaction.setTransactionId( printTransaction.getTransactionId() );

        transaction.setTransactionPaperQuantities( mapPaperQuantities(printTransaction) );

        return transaction;
    }

    @Override
    public TransactionDocument toTransactionDocumentPersistence(com.printerapp.domain.aggregates.transaction.entities.TransactionDocument domain) {
        if ( domain == null ) {
            return null;
        }

        TransactionDocument.TransactionDocumentBuilder transactionDocument = TransactionDocument.builder();

        transactionDocument.document( documentIdToDocument( domain.getId() ) );
        transactionDocument.paperType( domainDocumentDetailPaperType( domain ) );
        transactionDocument.numOfCopies( domainDocumentDetailNumOfCopies( domain ) );
        transactionDocument.isLandscape( domainDocumentDetailIsLandscape( domain ) );
        transactionDocument.fromPage( domainDocumentDetailFromPage( domain ) );
        transactionDocument.toPage( domainDocumentDetailToPage( domain ) );
        transactionDocument.leftSide( domainDocumentDetailLeftSide( domain ) );
        transactionDocument.rightSide( domainDocumentDetailRightSide( domain ) );
        transactionDocument.topSide( domainDocumentDetailTopSide( domain ) );
        transactionDocument.bottomSide( domainDocumentDetailBottomSide( domain ) );
        transactionDocument.isOneSide( domainDocumentDetailIsOneSide( domain ) );
        transactionDocument.numOfPageOneSide( domainDocumentDetailNumOfPageOneSide( domain ) );

        return transactionDocument.build();
    }

    @Override
    public com.printerapp.domain.aggregates.transaction.entities.TransactionDocument toTransactionDocumentDomain(TransactionDocument model) {
        if ( model == null ) {
            return null;
        }

        ObjectLink link = null;
        DocumentDetail documentDetail = null;

        link = documentToObjectLink( model.getDocument() );
        documentDetail = transactionDocumentToDocumentDetail( model );

        DocumentId documentId = null;

        com.printerapp.domain.aggregates.transaction.entities.TransactionDocument transactionDocument = new com.printerapp.domain.aggregates.transaction.entities.TransactionDocument( documentId, documentDetail, link );

        transactionDocument.setId( documentToDocumentId( model.getDocument() ) );
        transactionDocument.setCreatedAt( model.getCreatedAt() );
        transactionDocument.setUpdatedAt( model.getUpdatedAt() );

        return transactionDocument;
    }

    protected Customer userIdToCustomer(UserId userId) {
        if ( userId == null ) {
            return null;
        }

        Customer customer = new Customer();

        customer.setId( userId.getValue() );

        return customer;
    }

    protected Printer printerIdToPrinter(PrinterId printerId) {
        if ( printerId == null ) {
            return null;
        }

        Printer printer = new Printer();

        printer.setId( printerId.getValue() );

        return printer;
    }

    private UUID transactionIdValue(Transaction transaction) {
        if ( transaction == null ) {
            return null;
        }
        TransactionId id = transaction.getId();
        if ( id == null ) {
            return null;
        }
        UUID value = id.getValue();
        if ( value == null ) {
            return null;
        }
        return value;
    }

    protected Employee transactionToEmployee(Transaction transaction) {
        if ( transaction == null ) {
            return null;
        }

        Employee employee = new Employee();

        employee.setId( transaction.getEmployeeId()==null ? null : transaction.getEmployeeId().getValue() );

        return employee;
    }

    protected TransactionId printTransactionToTransactionId(PrintTransaction printTransaction) {
        if ( printTransaction == null ) {
            return null;
        }

        UUID value = null;

        value = printTransaction.getId();

        TransactionId transactionId = new TransactionId( value );

        return transactionId;
    }

    protected UserId customerToUserId(Customer customer) {
        if ( customer == null ) {
            return null;
        }

        UUID value = null;

        value = customer.getId();

        UserId userId = new UserId( value );

        return userId;
    }

    private String customerUserFirstName(Customer customer) {
        if ( customer == null ) {
            return null;
        }
        User user = customer.getUser();
        if ( user == null ) {
            return null;
        }
        String firstName = user.getFirstName();
        if ( firstName == null ) {
            return null;
        }
        return firstName;
    }

    private String customerUserLastName(Customer customer) {
        if ( customer == null ) {
            return null;
        }
        User user = customer.getUser();
        if ( user == null ) {
            return null;
        }
        String lastName = user.getLastName();
        if ( lastName == null ) {
            return null;
        }
        return lastName;
    }

    protected UserName customerToUserName(Customer customer) {
        if ( customer == null ) {
            return null;
        }

        String firstName = null;
        String lastName = null;

        firstName = customerUserFirstName( customer );
        lastName = customerUserLastName( customer );

        UserName userName = new UserName( firstName, lastName );

        return userName;
    }

    protected UserId employeeToUserId(Employee employee) {
        if ( employee == null ) {
            return null;
        }

        UUID value = null;

        value = employee.getId();

        UserId userId = new UserId( value );

        return userId;
    }

    private String employeeUserFirstName(Employee employee) {
        if ( employee == null ) {
            return null;
        }
        User user = employee.getUser();
        if ( user == null ) {
            return null;
        }
        String firstName = user.getFirstName();
        if ( firstName == null ) {
            return null;
        }
        return firstName;
    }

    private String employeeUserLastName(Employee employee) {
        if ( employee == null ) {
            return null;
        }
        User user = employee.getUser();
        if ( user == null ) {
            return null;
        }
        String lastName = user.getLastName();
        if ( lastName == null ) {
            return null;
        }
        return lastName;
    }

    protected UserName employeeToUserName(Employee employee) {
        if ( employee == null ) {
            return null;
        }

        String firstName = null;
        String lastName = null;

        firstName = employeeUserFirstName( employee );
        lastName = employeeUserLastName( employee );

        UserName userName = new UserName( firstName, lastName );

        return userName;
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

    protected Document documentIdToDocument(DocumentId documentId) {
        if ( documentId == null ) {
            return null;
        }

        Document document = new Document();

        document.setId( documentId.getValue() );

        return document;
    }

    private PaperType domainDocumentDetailPaperType(com.printerapp.domain.aggregates.transaction.entities.TransactionDocument transactionDocument) {
        if ( transactionDocument == null ) {
            return null;
        }
        DocumentDetail documentDetail = transactionDocument.getDocumentDetail();
        if ( documentDetail == null ) {
            return null;
        }
        PaperType paperType = documentDetail.getPaperType();
        if ( paperType == null ) {
            return null;
        }
        return paperType;
    }

    private Integer domainDocumentDetailNumOfCopies(com.printerapp.domain.aggregates.transaction.entities.TransactionDocument transactionDocument) {
        if ( transactionDocument == null ) {
            return null;
        }
        DocumentDetail documentDetail = transactionDocument.getDocumentDetail();
        if ( documentDetail == null ) {
            return null;
        }
        Integer numOfCopies = documentDetail.getNumOfCopies();
        if ( numOfCopies == null ) {
            return null;
        }
        return numOfCopies;
    }

    private Boolean domainDocumentDetailIsLandscape(com.printerapp.domain.aggregates.transaction.entities.TransactionDocument transactionDocument) {
        if ( transactionDocument == null ) {
            return null;
        }
        DocumentDetail documentDetail = transactionDocument.getDocumentDetail();
        if ( documentDetail == null ) {
            return null;
        }
        Boolean isLandscape = documentDetail.getIsLandscape();
        if ( isLandscape == null ) {
            return null;
        }
        return isLandscape;
    }

    private Integer domainDocumentDetailFromPage(com.printerapp.domain.aggregates.transaction.entities.TransactionDocument transactionDocument) {
        if ( transactionDocument == null ) {
            return null;
        }
        DocumentDetail documentDetail = transactionDocument.getDocumentDetail();
        if ( documentDetail == null ) {
            return null;
        }
        Integer fromPage = documentDetail.getFromPage();
        if ( fromPage == null ) {
            return null;
        }
        return fromPage;
    }

    private Integer domainDocumentDetailToPage(com.printerapp.domain.aggregates.transaction.entities.TransactionDocument transactionDocument) {
        if ( transactionDocument == null ) {
            return null;
        }
        DocumentDetail documentDetail = transactionDocument.getDocumentDetail();
        if ( documentDetail == null ) {
            return null;
        }
        Integer toPage = documentDetail.getToPage();
        if ( toPage == null ) {
            return null;
        }
        return toPage;
    }

    private Integer domainDocumentDetailLeftSide(com.printerapp.domain.aggregates.transaction.entities.TransactionDocument transactionDocument) {
        if ( transactionDocument == null ) {
            return null;
        }
        DocumentDetail documentDetail = transactionDocument.getDocumentDetail();
        if ( documentDetail == null ) {
            return null;
        }
        Integer leftSide = documentDetail.getLeftSide();
        if ( leftSide == null ) {
            return null;
        }
        return leftSide;
    }

    private Integer domainDocumentDetailRightSide(com.printerapp.domain.aggregates.transaction.entities.TransactionDocument transactionDocument) {
        if ( transactionDocument == null ) {
            return null;
        }
        DocumentDetail documentDetail = transactionDocument.getDocumentDetail();
        if ( documentDetail == null ) {
            return null;
        }
        Integer rightSide = documentDetail.getRightSide();
        if ( rightSide == null ) {
            return null;
        }
        return rightSide;
    }

    private Integer domainDocumentDetailTopSide(com.printerapp.domain.aggregates.transaction.entities.TransactionDocument transactionDocument) {
        if ( transactionDocument == null ) {
            return null;
        }
        DocumentDetail documentDetail = transactionDocument.getDocumentDetail();
        if ( documentDetail == null ) {
            return null;
        }
        Integer topSide = documentDetail.getTopSide();
        if ( topSide == null ) {
            return null;
        }
        return topSide;
    }

    private Integer domainDocumentDetailBottomSide(com.printerapp.domain.aggregates.transaction.entities.TransactionDocument transactionDocument) {
        if ( transactionDocument == null ) {
            return null;
        }
        DocumentDetail documentDetail = transactionDocument.getDocumentDetail();
        if ( documentDetail == null ) {
            return null;
        }
        Integer bottomSide = documentDetail.getBottomSide();
        if ( bottomSide == null ) {
            return null;
        }
        return bottomSide;
    }

    private Boolean domainDocumentDetailIsOneSide(com.printerapp.domain.aggregates.transaction.entities.TransactionDocument transactionDocument) {
        if ( transactionDocument == null ) {
            return null;
        }
        DocumentDetail documentDetail = transactionDocument.getDocumentDetail();
        if ( documentDetail == null ) {
            return null;
        }
        Boolean isOneSide = documentDetail.getIsOneSide();
        if ( isOneSide == null ) {
            return null;
        }
        return isOneSide;
    }

    private Integer domainDocumentDetailNumOfPageOneSide(com.printerapp.domain.aggregates.transaction.entities.TransactionDocument transactionDocument) {
        if ( transactionDocument == null ) {
            return null;
        }
        DocumentDetail documentDetail = transactionDocument.getDocumentDetail();
        if ( documentDetail == null ) {
            return null;
        }
        Integer numOfPageOneSide = documentDetail.getNumOfPageOneSide();
        if ( numOfPageOneSide == null ) {
            return null;
        }
        return numOfPageOneSide;
    }

    protected ObjectLink documentToObjectLink(Document document) {
        if ( document == null ) {
            return null;
        }

        String value = null;

        value = document.getLink();

        ObjectLink objectLink = new ObjectLink( value );

        return objectLink;
    }

    protected DocumentId documentToDocumentId(Document document) {
        if ( document == null ) {
            return null;
        }

        UUID value = null;

        value = document.getId();

        DocumentId documentId = new DocumentId( value );

        return documentId;
    }

    protected DocumentDetail transactionDocumentToDocumentDetail(TransactionDocument transactionDocument) {
        if ( transactionDocument == null ) {
            return null;
        }

        PaperType paperType = null;
        Integer numOfCopies = null;
        Boolean isLandscape = null;
        Integer fromPage = null;
        Integer toPage = null;
        Integer leftSide = null;
        Integer rightSide = null;
        Integer topSide = null;
        Integer bottomSide = null;
        Boolean isOneSide = null;
        Integer numOfPageOneSide = null;

        paperType = transactionDocument.getPaperType();
        numOfCopies = transactionDocument.getNumOfCopies();
        isLandscape = transactionDocument.getIsLandscape();
        fromPage = transactionDocument.getFromPage();
        toPage = transactionDocument.getToPage();
        leftSide = transactionDocument.getLeftSide();
        rightSide = transactionDocument.getRightSide();
        topSide = transactionDocument.getTopSide();
        bottomSide = transactionDocument.getBottomSide();
        isOneSide = transactionDocument.getIsOneSide();
        numOfPageOneSide = transactionDocument.getNumOfPageOneSide();

        DocumentDetail documentDetail = new DocumentDetail( paperType, numOfCopies, isLandscape, fromPage, toPage, leftSide, rightSide, topSide, bottomSide, isOneSide, numOfPageOneSide );

        return documentDetail;
    }
}
