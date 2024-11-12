package com.example.printer_api.modules.users.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.printer_api.modules.users.dtos.response.UserReponse;
import com.example.printer_api.modules.users.dtos.resquest.CreateUserRequest;
import com.example.printer_api.modules.users.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    // @Mapping(target = "customer", source = "customerRequest")
    // @Mapping(target = "employee", source = "employeeRequest")
    // @Mapping(target = "createdAt", ignore = true)
    // @Mapping(target = "updatedAt", ignore = true)
    // @Mapping(target = "id", ignore = true)
    User toUser(CreateUserRequest dto);

    // @Mapping(target = "firstName", source = "firstName") // Explicitly map
    // firstName
    // @Mapping(target = "lastName", source = "lastName") // Explicitly map lastName
    // @Mapping(target = "email", source = "email") // Explicitly map email
    // @Mapping(target = "phoneNumber", source = "phoneNumber") // Explicitly map
    // phoneNumber
    // @Mapping(target = "role", source = "role")
    // @Mapping(target = "customer", source = "customer")
    // @Mapping(target = "employee", source = "employee")
    UserReponse toUserResponse(User user);
}
