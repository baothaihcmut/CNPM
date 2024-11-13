package com.example.printer_api.modules.users.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import com.example.printer_api.shared.database.BaseEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Table(
        name = "users",
        indexes = {@Index(name = "idx_email", columnList = "email")})
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

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    Customer customer;

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    Employee employee;

    @PrePersist
    private void setDefaultValue() {
        if (this.isActive == null) {
            this.isActive = false;
        }
    }
}
