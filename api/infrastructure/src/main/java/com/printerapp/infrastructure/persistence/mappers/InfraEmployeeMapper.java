package com.printerapp.infrastructure.persistence.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.printerapp.domain.aggregates.employee.Employee;

@Mapper(componentModel = "spring")
public interface InfraEmployeeMapper {

        @Mappings(value = {
                        @Mapping(source = "id.value", target = "user.id"),
                        @Mapping(source = "id.value", target = "id"),
        })
        com.printerapp.infrastructure.persistence.models.Employee toPersistence(Employee employee);

        @Mappings(value = {
                        @Mapping(source = "id", target = "id.value"),
        })
        Employee toDomain(com.printerapp.infrastructure.persistence.models.Employee employee);

        // Default methods to handle custom mapping where necessary

}
