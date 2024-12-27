package com.printerapp.infrastructure.persistence.mappers;

import com.printerapp.domain.aggregates.employee.Employee;
import com.printerapp.domain.aggregates.user.value_objects.UserId;
import com.printerapp.infrastructure.persistence.models.User;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-03T19:01:52+0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.40.0.z20241112-1021, environment: Java 17.0.13 (Eclipse Adoptium)"
)
@Component
public class InfraEmployeeMapperImpl implements InfraEmployeeMapper {

    @Override
    public com.printerapp.infrastructure.persistence.models.Employee toPersistence(Employee employee) {
        if ( employee == null ) {
            return null;
        }

        com.printerapp.infrastructure.persistence.models.Employee employee1 = new com.printerapp.infrastructure.persistence.models.Employee();

        employee1.setUser( userIdToUser( employee.getId() ) );
        employee1.setId( employeeIdValue( employee ) );
        employee1.setCreatedAt( employee.getCreatedAt() );
        employee1.setUpdatedAt( employee.getUpdatedAt() );
        employee1.setEmployeeId( employee.getEmployeeId() );
        employee1.setIsOnWork( employee.getIsOnWork() );
        employee1.setStartWorkDate( employee.getStartWorkDate() );

        return employee1;
    }

    @Override
    public Employee toDomain(com.printerapp.infrastructure.persistence.models.Employee employee) {
        if ( employee == null ) {
            return null;
        }

        String employeeId = null;

        employeeId = employee.getEmployeeId();

        UserId userId = null;

        Employee employee1 = new Employee( userId, employeeId );

        employee1.setId( employeeToUserId( employee ) );
        employee1.setCreatedAt( employee.getCreatedAt() );
        employee1.setUpdatedAt( employee.getUpdatedAt() );
        employee1.setIsOnWork( employee.getIsOnWork() );
        employee1.setStartWorkDate( employee.getStartWorkDate() );

        return employee1;
    }

    protected User userIdToUser(UserId userId) {
        if ( userId == null ) {
            return null;
        }

        User user = new User();

        user.setId( userId.getValue() );

        return user;
    }

    private UUID employeeIdValue(Employee employee) {
        if ( employee == null ) {
            return null;
        }
        UserId id = employee.getId();
        if ( id == null ) {
            return null;
        }
        UUID value = id.getValue();
        if ( value == null ) {
            return null;
        }
        return value;
    }

    protected UserId employeeToUserId(com.printerapp.infrastructure.persistence.models.Employee employee) {
        if ( employee == null ) {
            return null;
        }

        UUID value = null;

        value = employee.getId();

        UserId userId = new UserId( value );

        return userId;
    }
}
