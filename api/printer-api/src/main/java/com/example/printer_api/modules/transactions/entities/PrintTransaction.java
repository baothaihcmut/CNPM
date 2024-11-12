package com.example.printer_api.modules.transactions.entities;

import java.util.List;

import com.example.printer_api.modules.printers.entities.Printer;
import com.example.printer_api.modules.transaction_docments.entities.TransactionDocument;
import com.example.printer_api.modules.users.entities.Customer;
import com.example.printer_api.modules.users.entities.Employee;
import com.example.printer_api.shared.database.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "print_transactions")
public class PrintTransaction extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = true)
    @JsonBackReference
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "printer_id", nullable = true)
    @JsonBackReference
    private Printer printer;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = true)
    @JsonBackReference
    private Employee employee;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PrintTransactionStatus status;

    @Column(nullable = false)
    private Integer totalNumOfPaperA3;

    @Column(nullable = false)
    private Integer totalNumOfPaperA4;

    @Column(nullable = false)
    private Integer totalNumOfPaperA5;

    @Column(nullable = true)
    private String comment;

    @OneToMany(mappedBy = "transaction", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<TransactionDocument> documents;
}
