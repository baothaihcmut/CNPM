package com.printerapp.infrastructure.persistence.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.printerapp.domain.aggregates.user.User;
import com.printerapp.domain.aggregates.user.value_objects.Email;
import com.printerapp.domain.enums.Role;

@Mapper(componentModel = "spring")
public interface InfraUserMapper {

    @Mappings(value = {
            @Mapping(source = "id.value", target = "id"),
            @Mapping(source = "email.value", target = "email"),
            @Mapping(source = "name.firstName", target = "firstName"),
            @Mapping(source = "name.lastName", target = "lastName")
    })
    com.printerapp.infrastructure.persistence.models.User toPersistenct(User user);

    @Mappings(value = {
            @Mapping(source = "id", target = "id.value"),
            @Mapping(target = "name.firstName", source = "firstName"),
            @Mapping(target = "name.lastName", source = "lastName"),
            @Mapping(expression = "java(mapEmail(model.getEmail(),model.getRole()))", target = "email"),
    })
    User toDomain(com.printerapp.infrastructure.persistence.models.User model);

    default Email mapEmail(String email, Role role) {
        return new Email(email, role);
    }

}
