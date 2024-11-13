package com.example.printer_api.modules.users.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import com.example.printer_api.modules.documents.entities.Document;
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
@Table(name = "customers")
public class Customer extends BaseEntity {
    @OneToOne
    @MapsId
    @JoinColumn(name = "id", nullable = false)
    @JsonBackReference
    private User user;

    @Column(nullable = false)
    private String studentId;

    @Column(nullable = false)
    private Integer numOfPapersA3;

    @Column(nullable = false)
    private Integer numOfPapersA4;

    @Column(nullable = false)
    private Integer numOfPapersA5;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<PrintTransaction> transactions;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<Document> documents;

    @PrePersist
    private void setDefaultValue() {
        if (this.numOfPapersA3 == null) {
            this.numOfPapersA3 = 0;
        }
        if (this.numOfPapersA4 == null) {
            this.numOfPapersA4 = 0;
        }
        if (this.numOfPapersA5 == null) {
            this.numOfPapersA5 = 0;
        }
    }
}
