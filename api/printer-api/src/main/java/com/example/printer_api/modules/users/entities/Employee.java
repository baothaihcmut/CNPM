package com.example.printer_api.modules.users.entities;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import com.example.printer_api.modules.printer_employees.entities.PrinterEmployee;
import com.example.printer_api.modules.transactions.entities.PrintTransaction;
import com.example.printer_api.shared.database.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "employees")
public class Employee extends BaseEntity {
    @MapsId
    @OneToOne
    @JoinColumn(name = "id", nullable = false)
    @JsonBackReference
    private User user;

    @Column(nullable = true)
    private LocalDate startWorkDate;

    @Column(nullable = false)
    private Boolean isOnWork;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<PrinterEmployee> printers;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<PrintTransaction> transactions;
}
