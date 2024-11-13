package com.example.printer_api.modules.printer_employees.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import com.example.printer_api.modules.printers.entities.Printer;
import com.example.printer_api.modules.users.entities.Employee;
import com.example.printer_api.shared.database.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "printer_employees")
public class PrinterEmployee extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    @JsonBackReference
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "printer_id", nullable = false)
    @JsonBackReference
    private Printer printer;

    @Column(nullable = false)
    private Integer numOfTransactions;

    @PrePersist
    private void setDefaultValue() {
        if (this.numOfTransactions == null) {
            this.numOfTransactions = 0;
        }
    }
}
