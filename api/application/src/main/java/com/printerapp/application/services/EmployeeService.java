package com.printerapp.application.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.printerapp.application.exceptions.ForbiddenException;
import com.printerapp.application.exceptions.NotFoundException;
import com.printerapp.application.mappers.ApplicationEmployeeMapper;
import com.printerapp.application.mappers.ApplicationPrinterMapper;
import com.printerapp.application.queries.employees.FindTransactionOfEmployeeQuery;
import com.printerapp.application.queries.transactions.SearchTransactionQuery;
import com.printerapp.application.results.auth.UserContext;
import com.printerapp.application.results.employee.EmployeePrinterResult;
import com.printerapp.application.results.employee.EmployeeTransactionResult;
import com.printerapp.application.results.printers.PrinterResult;
import com.printerapp.domain.aggregates.printer.Printer;
import com.printerapp.domain.aggregates.printer.value_objects.PrinterId;
import com.printerapp.domain.aggregates.printer_employee.PrinterEmployee;
import com.printerapp.domain.aggregates.printer_employee.value_objects.PrinterEmployeeId;
import com.printerapp.domain.aggregates.transaction.Transaction;
import com.printerapp.domain.aggregates.user.value_objects.UserId;
import com.printerapp.domain.common.filter.FilterParam;
import com.printerapp.domain.common.filter.Operator;
import com.printerapp.domain.common.pagination.PaginatedResult;
import com.printerapp.domain.repositories.EmployeeRepository;
import com.printerapp.domain.repositories.PrinterEmployeeRepository;
import com.printerapp.domain.repositories.PrinterRepository;
import com.printerapp.domain.repositories.TransactionRepositoy;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeService {
        private final PrinterEmployeeRepository printerEmployeeRepository;
        private final AuthService authService;
        private final TransactionRepositoy transactionRepositoy;
        private final ApplicationEmployeeMapper employeeMapper;
        private final EmployeeRepository employeeRepository;
        private final PrinterRepository printerRepository;
        private final ApplicationPrinterMapper printerMapper;

        public List<EmployeePrinterResult> findAllPrinterOfEmployee() {
                // get user context
                UserContext userContext = this.authService.getUserContext();
                // check if employee exist
                UserId employeeId = new UserId(userContext.getId());
                this.employeeRepository.findById(employeeId)
                                .orElseThrow(() -> new NotFoundException("Employee not exist"));

                List<PrinterEmployee> printerEmployees = this.printerEmployeeRepository
                                .findByIdEmployeeId(new UserId(userContext.getId()));
                return printerEmployees.stream()
                                .map((printerEmployee) -> this.employeeMapper.toEmployeePrinterResult(printerEmployee))
                                .toList();
        }

        public List<PrinterResult> findAllPrinterOfEmployeeById(UserId employeeId) {
                // get user context
                // check if employee exist
                this.employeeRepository.findById(employeeId)
                                .orElseThrow(() -> new NotFoundException("Employee not exist"));

                List<Printer> printerEmployees = this.printerRepository.findAllPrinterOfEmployee(employeeId);
                return printerEmployees.stream()
                                .map((printerEmployee) -> this.printerMapper.toResult(printerEmployee))
                                .toList();
        }

        public PaginatedResult<EmployeeTransactionResult> findAllTransactionOfEmployee(
                        FindTransactionOfEmployeeQuery query) {
                UUID employeeId = query.getEmployeeId() != null ? query.getEmployeeId().getValue()
                                : this.authService.getUserContext().getId();
                // build filter
                List<FilterParam<?>> filters = new ArrayList<>();
                filters.add(FilterParam.builder().field("employee.id").operator(Operator.EQUAL)
                                .value(employeeId).build());
                if (query.getPrinterId() != null) {
                        filters.add(FilterParam.builder().field("printer.id").operator(Operator.EQUAL)
                                        .value(query.getPrinterId().getValue()).build());
                }
                if (query.getStatus() != null) {
                        filters.add(FilterParam.builder().field("status").operator(Operator.EQUAL)
                                        .value(query.getStatus()).build());
                }
                System.out.println(filters);
                PaginatedResult<Transaction> transactions = this.transactionRepositoy
                                .findByCriteria(
                                                filters,
                                                query.getSortParam() == null ? new ArrayList<>()
                                                                : List.of(query.getSortParam()),
                                                query.getPaginatedParam());
                return PaginatedResult.of(
                                transactions.getData().stream()
                                                .map((transaction) -> this.employeeMapper
                                                                .toEmployeeTransactionResult(transaction))
                                                .toList(),
                                transactions.getPage(),
                                transactions.getSize(), transactions.getTotalElements());
        }

        public void checkPermissionOfEmployee(UUID id) {
                PrinterId printerId = new PrinterId(id);
                UserContext userContext = this.authService.getUserContext();
                // else check if user is employee of this printer
                this.printerEmployeeRepository
                                .findById(new PrinterEmployeeId(new UserId(userContext.getId()), printerId))
                                .orElseThrow(() -> new ForbiddenException("You aren't employee of this printer"));

        }

        public PaginatedResult<EmployeeTransactionResult> searchTransactionOfEmployee(SearchTransactionQuery search) {
                UserContext userContext = this.authService.getUserContext();
                String[] fields = { "name", "customer.user.firstName", "customer.user.lastName", "customer.customerId",
                                "transactionId" };
                List<FilterParam<?>> filters = new ArrayList<>();
                for (String field : fields) {
                        filters.add(FilterParam.builder().field(field).value(search.getSearch())
                                        .operator(Operator.LIKE).build());
                }
                FilterParam<?> idCriteria = FilterParam.builder().field("employee.id").operator(Operator.EQUAL)
                                .value(userContext.getId()).build();
                FilterParam<?> statusCriteria = FilterParam.builder().field("status").operator(Operator.EQUAL)
                                .value(search.getStatus()).build();
                FilterParam<?> printerCriteria = FilterParam.builder().field("printer.id").operator(Operator.EQUAL)
                                .value(search.getPrinterId().getValue()).build();
                PaginatedResult<Transaction> res = this.transactionRepositoy.search(
                                List.of(idCriteria, statusCriteria, printerCriteria),
                                filters,
                                search.getSortParam() == null ? new ArrayList<>() : List.of(search.getSortParam()),
                                search.getPaginatedParam());

                return PaginatedResult.of(res.getData().stream()
                                .map((tramsacton) -> this.employeeMapper.toEmployeeTransactionResult(tramsacton))
                                .toList(),
                                res.getPage(), res.getSize(), res.getTotalElements());
        }

}
