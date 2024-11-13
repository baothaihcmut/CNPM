package com.example.printer_api.modules.users.services;

import jakarta.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.printer_api.modules.users.dtos.response.UserReponse;
import com.example.printer_api.modules.users.dtos.resquest.CreateUserRequest;
import com.example.printer_api.modules.users.entities.Customer;
import com.example.printer_api.modules.users.entities.Employee;
import com.example.printer_api.modules.users.entities.Role;
import com.example.printer_api.modules.users.entities.User;
import com.example.printer_api.modules.users.mappers.UserMapper;
import com.example.printer_api.modules.users.repositories.CustomerRepository;
import com.example.printer_api.modules.users.repositories.EmployeeRepository;
import com.example.printer_api.modules.users.repositories.UserRepository;
import com.example.printer_api.shared.exception.AppException;
import com.example.printer_api.shared.exception.StatusCode;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserMapper userMapper;

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Transactional()
    public UserReponse initUserInfo(CreateUserRequest dto) {
        String email = "bao.thaikhmt@hcmut.edu.vn";
        User userWithEmail = this.userRepository
                .findUserByEmail(email)
                .orElseThrow(() -> new AppException(StatusCode.EMAIL_NOT_EXIST));
        if (userWithEmail.getIsActive()) {
            throw new AppException(StatusCode.USER_INFO_EXIST);
        }
        User user = this.userMapper.toUser(dto);
        user.setIsActive(true);
        user.setEmail(userWithEmail.getEmail());
        user.setRole(userWithEmail.getRole());
        user.setId(userWithEmail.getId());
        user = this.userRepository.update(user);
        switch (user.getRole()) {
            case Role.CUSTOMER:
                Customer customer = user.getCustomer();
                if (customer == null) {
                    throw new AppException(StatusCode.DETAIL_REQUIRED);
                }
                customer.setUser(user);
                user.setCustomer(this.customerRepository.create(customer));
                break;
            case Role.EMPLOYEE:
                Employee employee = user.getEmployee();
                if (employee == null) {
                    throw new AppException(StatusCode.DETAIL_REQUIRED);
                }
                employee.setUser(user);
                user.setEmployee(this.employeeRepository.create(employee));
                break;
            default:
                break;
        }
        return this.userMapper.toUserResponse(user);
    }

    @Transactional
    public User createEmail(String email, Role role) {
        User user = new User();
        user.setEmail(email);
        user.setRole(role);
        user = this.userRepository.create(user);
        return user;
    }
}
