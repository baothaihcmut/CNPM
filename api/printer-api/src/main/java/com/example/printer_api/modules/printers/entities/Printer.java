package com.example.printer_api.modules.printers.entities;

import java.util.List;

import com.example.printer_api.modules.printer_employees.entities.PrinterEmployee;
import com.example.printer_api.modules.transactions.entities.PrintTransaction;
import com.example.printer_api.shared.database.BaseEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.persistence.CascadeType;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "printers")
public class Printer extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PrinterStatus status;

    @OneToMany(mappedBy = "printer", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<PrinterEmployee> employees;

    @OneToMany(mappedBy = "printer", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<PrintTransaction> transactions;
}
