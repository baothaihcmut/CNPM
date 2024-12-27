package com.printerapp.domain.aggregates.user;

import java.time.LocalDateTime;
import java.util.UUID;

import com.printerapp.domain.aggregates.user.value_objects.Email;
import com.printerapp.domain.aggregates.user.value_objects.UserId;
import com.printerapp.domain.aggregates.user.value_objects.UserName;
import com.printerapp.domain.enums.Role;
import com.printerapp.domain.models.BaseAggregate;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class User extends BaseAggregate<UserId> {
    private UserName name;
    private String phoneNumber;
    private Email email;
    private Role role;
    private Boolean isActive;

    public User() {
        super(new UserId(UUID.randomUUID()));
    }

    public User(Email email, Role role) {
        super(new UserId(UUID.randomUUID()));
        this.email = email;
        this.role = role;
        this.isActive = false;
    }

    public User(UserName userName, String phoneNumber, Email email, Role role) {
        super(new UserId(UUID.randomUUID()));
        this.name = userName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.role = role;
        this.isActive = true;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

}
