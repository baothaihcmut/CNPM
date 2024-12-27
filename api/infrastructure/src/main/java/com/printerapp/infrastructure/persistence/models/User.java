package com.printerapp.infrastructure.persistence.models;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.printerapp.domain.enums.Role;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "users", indexes = {
        @Index(name = "idx_email", columnList = "email")
})
public class User extends BaseEntity {
    @Column()
    private String firstName;

    @Column()
    private String lastName;

    @Column(unique = true)
    private String email;

    @Column()
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private Boolean isActive;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Customer customer;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Employee employee;

    public User(UUID id, String firstName, String lastName, String email, String phoneNumber, Role role,
            Boolean isActive) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.isActive = isActive;
    }
}
