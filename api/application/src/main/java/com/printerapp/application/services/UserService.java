package com.printerapp.application.services;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.printerapp.application.commands.printers.AddEmployeeToPrinterCommand;
import com.printerapp.application.commands.users.ActivateUserCommand;
import com.printerapp.application.commands.users.AddEmployeeMailCommand;
import com.printerapp.application.exceptions.ConflictException;
import com.printerapp.application.exceptions.NotFoundException;
import com.printerapp.application.mappers.ApplicationUserMapper;
import com.printerapp.application.queries.users.FindUserByIdQuery;
import com.printerapp.application.queries.users.FindUserQuery;
import com.printerapp.application.queries.users.SearchUserQuery;
import com.printerapp.application.results.auth.UserContext;
import com.printerapp.application.results.users.AddEmployeeMailResult;
import com.printerapp.application.results.users.UserResult;
import com.printerapp.domain.aggregates.customer.Customer;
import com.printerapp.domain.aggregates.employee.Employee;
import com.printerapp.domain.aggregates.user.User;
import com.printerapp.domain.aggregates.user.value_objects.UserId;
import com.printerapp.domain.aggregates.user.value_objects.UserName;
import com.printerapp.domain.common.filter.FilterParam;
import com.printerapp.domain.common.filter.Operator;
import com.printerapp.domain.common.pagination.PaginatedResult;
import com.printerapp.domain.enums.Role;
import com.printerapp.domain.repositories.CustomerRepository;
import com.printerapp.domain.repositories.EmployeeRepository;
import com.printerapp.domain.repositories.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    private final AuthService authService;
    private final ApplicationUserMapper userMapper;
    private final PrinterService printerService;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    private static String generateRandomEmployeeId(int length) {
        if (length < 1)
            throw new IllegalArgumentException("Length must be greater than 0");

        StringBuilder result = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(CHARACTERS.length());
            result.append(CHARACTERS.charAt(index));
        }
        return result.toString();
    }

    @Transactional
    public UserResult activateUser(ActivateUserCommand createUserCommand) {
        // create aggregate
        UserContext userContext = this.authService.getUserContext();
        System.out.println(userContext.getRole()[0]);
        // get user by email if email not exist throw not found exception
        User user = this.userRepository.findById(new UserId(userContext.getId()))
                .orElseThrow(() -> new NotFoundException("Email not exist"));

        // init user info
        user.setName(new UserName(createUserCommand.getFirstName(), createUserCommand.getLastName()));
        user.setPhoneNumber(createUserCommand.getPhoneNumber());
        // activate user
        user.setIsActive(true);
        // persist to db
        this.userRepository.save(user);
        // if user is customer
        if (userContext.getRole()[0].equals(Role.CUSTOMER)) {
            // create customer aggregate
            Customer customer = new Customer(user.getId(),
                    createUserCommand.getCustomerInfo().getCustomerId());
            // persist to db
            this.customerRepository.save(customer);
            return this.userMapper.toActivateUserResult(user, customer, null);
        }
        if (userContext.getRole()[0].equals(Role.EMPLOYEE)) {
            Employee employee = this.employeeRepository.findById(user.getId())
                    .orElseThrow(() -> new NotFoundException("Employee not found"));
            employee.activate();
            this.employeeRepository.save(employee);
            return this.userMapper.toActivateUserResult(user, null, employee);
        }
        // if no role match throw wrong type
        throw new IllegalArgumentException("Wrong role");
    }

    public UserResult findUserById(FindUserByIdQuery findUserByIdQuery) {
        User user = this.userRepository.findById(findUserByIdQuery.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found"));
        if (user.getRole().equals(Role.CUSTOMER)) {
            Customer customer = this.customerRepository.findById(findUserByIdQuery.getUserId())
                    .orElseThrow(() -> new NotFoundException("User not found or user not customer"));
            return this.userMapper.toActivateUserResult(user, customer, null);
        } else {
            Employee employee = this.employeeRepository.findById(findUserByIdQuery.getUserId())
                    .orElseThrow(() -> new NotFoundException("User not found or user not employee"));
            return this.userMapper.toActivateUserResult(user, null, employee);
        }
    }

    @Transactional
    public AddEmployeeMailResult addEmployeeMail(AddEmployeeMailCommand command) {
        // check if email exist
        User existUser = this.userRepository.findByEmail(command.getEmail()).orElse(null);
        if (existUser != null) {
            throw new ConflictException("Email exist");
        }
        User userWithEmail = new User(command.getEmail(), Role.EMPLOYEE);
        Employee employee = new Employee(userWithEmail.getId(), UserService.generateRandomEmployeeId(8));
        // persist user
        this.userRepository.save(userWithEmail);
        // persist employee
        this.employeeRepository.save(employee);
        command.getPrinters().forEach((printer) -> {
            this.printerService.addEmployeeToPrinter(AddEmployeeToPrinterCommand.builder().employeeId(employee.getId())
                    .printerId(printer.getPrinterId()).isManager(printer.getIsManger()).build());
        });
        return this.userMapper.toAddEmployeeMailResult(userWithEmail, employee);
    }

    public PaginatedResult<UserResult> searchUser(SearchUserQuery search) {
        String[] fields = { "firstName", "lastName", "email", "phoneNumber", "customer.customerId",
                "employee.employeeId" };
        List<FilterParam<?>> filters = new ArrayList<>();
        for (String field : fields) {
            filters.add(FilterParam.builder().field(field).value(search.getSearch())
                    .operator(Operator.LIKE).build());
        }
        System.out.println(filters);
        FilterParam<?> roleCriteria = FilterParam.builder().field("role").operator(Operator.EQUAL)
                .value(search.getRole()).build();
        System.out.println(roleCriteria);
        PaginatedResult<User> res = this.userRepository.search(filters,
                List.of(roleCriteria),
                search.getSortParam() == null ? new ArrayList<>() : List.of(search.getSortParam()),
                search.getPaginatedParam());

        return PaginatedResult.of(res.getData().stream()
                .map((user) -> this.userMapper.toUserResult(user))
                .toList(),
                res.getPage(), res.getSize(), res.getTotalElements());
    }

    public PaginatedResult<UserResult> findUser(FindUserQuery query) {
        List<FilterParam<?>> filters = new ArrayList<>();
        if (query.getRole() != null) {
            filters.add(FilterParam.builder().field("role").operator(Operator.EQUAL).value(query.getRole()).build());
        }
        System.out.println(filters);
        PaginatedResult<User> res = this.userRepository.findByCriteria(filters,
                query.getSortParam() == null ? new ArrayList<>() : List.of(query.getSortParam()),
                query.getPaginatedParam());
        return PaginatedResult.of(res.getData().stream()
                .map((user) -> this.userMapper.toUserResult(user))
                .toList(),
                res.getPage(), res.getSize(), res.getTotalElements());
    }
}
