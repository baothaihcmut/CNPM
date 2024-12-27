package com.printerapp.application.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.printerapp.application.mappers.ApplicationCustomerMapper;
import com.printerapp.application.queries.customers.FindDocumentQuery;
import com.printerapp.application.queries.storage.GetPresignUrlGetQuery;
import com.printerapp.application.queries.transactions.FindTransactionQuery;
import com.printerapp.application.queries.transactions.SearchTransactionQuery;
import com.printerapp.application.results.auth.UserContext;
import com.printerapp.application.results.cusomers.CustomerDocumentResult;
import com.printerapp.application.results.cusomers.CustomerTransactionResult;
import com.printerapp.domain.aggregates.document.Document;
import com.printerapp.domain.aggregates.transaction.Transaction;
import com.printerapp.domain.aggregates.user.value_objects.UserId;
import com.printerapp.domain.common.filter.FilterParam;
import com.printerapp.domain.common.filter.Operator;
import com.printerapp.domain.common.pagination.PaginatedResult;
import com.printerapp.domain.repositories.DocumentRepository;
import com.printerapp.domain.repositories.TransactionRepositoy;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {
        private final DocumentRepository documentRepository;
        private final TransactionRepositoy transactionRepositoy;
        private final AuthService authService;
        private final ApplicationCustomerMapper customerMapper;
        private final StorageService storageService;

        public List<CustomerDocumentResult> findDocumentOfUser() {
                UserContext userContext = this.authService.getUserContext();
                UserId userId = new UserId(userContext.getId());
                return this.documentRepository.findAllDocumentOfUser(userId).stream()
                                .map((document) -> new CustomerDocumentResult(document.getId(), document.getName(),
                                                document.getLink().getValue()))
                                .toList();
        }

        public List<CustomerDocumentResult> findDocumentOfUser(FindDocumentQuery query) {
                List<Document> documents = this.documentRepository.findAllDocumentOfUser(query.getCustomerId());
                return documents.stream().map((doc) -> new CustomerDocumentResult(
                                doc.getId(),
                                doc.getName(),
                                storageService.getPresignUrlGet(GetPresignUrlGetQuery.builder()
                                                .objectName(doc.getLink().getValue()).build()).getUrl()))
                                .toList();
        }

        public PaginatedResult<CustomerTransactionResult> findTractionOfCustomer(FindTransactionQuery query) {
                UserContext userContext = this.authService.getUserContext();
                List<FilterParam<?>> filters = new ArrayList<>();
                if (query.getStatus() != null) {
                        filters.add(
                                        FilterParam.builder().field("status").value(query.getStatus())
                                                        .operator(Operator.EQUAL).build());
                }
                filters.add(
                                FilterParam.builder().field("customer.id").value(userContext.getId())
                                                .operator(Operator.EQUAL).build());
                PaginatedResult<Transaction> res = this.transactionRepositoy.findByCriteria(filters,
                                query.getSortParam() == null ? new ArrayList<>() : List.of(query.getSortParam()),
                                query.getPaginatedParam());
                return PaginatedResult.of(res.getData().stream()
                                .map((tramsacton) -> this.customerMapper.toCustomerTransactionResult(tramsacton))
                                .toList(),
                                res.getPage(), res.getSize(), res.getTotalElements());
        }

        public PaginatedResult<CustomerTransactionResult> searchTransactionOfUser(SearchTransactionQuery search) {
                UserContext userContext = this.authService.getUserContext();
                String[] fields = { "name", "employee.user.firstName", "employee.user.lastName", "employee.employeeId",
                                "transactionId" };
                List<FilterParam<?>> filters = new ArrayList<>();
                for (String field : fields) {
                        filters.add(FilterParam.builder().field(field).value(search.getSearch())
                                        .operator(Operator.LIKE).build());
                }
                FilterParam<?> idCriteria = FilterParam.builder().field("customer.id").operator(Operator.EQUAL)
                                .value(userContext.getId()).build();
                FilterParam<?> statusCriteria = FilterParam.builder().field("status").operator(Operator.EQUAL)
                                .value(search.getStatus()).build();
                PaginatedResult<Transaction> res = this.transactionRepositoy.search(List.of(idCriteria, statusCriteria),
                                filters,
                                search.getSortParam() == null ? new ArrayList<>() : List.of(search.getSortParam()),
                                search.getPaginatedParam());

                return PaginatedResult.of(res.getData().stream()
                                .map((tramsacton) -> this.customerMapper.toCustomerTransactionResult(tramsacton))
                                .toList(),
                                res.getPage(), res.getSize(), res.getTotalElements());

        }
}
