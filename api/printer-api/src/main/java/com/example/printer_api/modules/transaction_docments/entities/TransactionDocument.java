package com.example.printer_api.modules.transaction_docments.entities;

import com.example.printer_api.modules.documents.entities.Document;
import com.example.printer_api.modules.transactions.entities.PrintTransaction;
import com.example.printer_api.shared.database.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
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
@Table(name = "transaction_documents")
public class TransactionDocument extends BaseEntity {
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaperType paperType;

    @Column(nullable = false)
    private Integer numOfCopies;

    @Column(nullable = false)
    private Boolean isLandscape;

    @Column(nullable = true)
    private Integer fromPage;

    @Column(nullable = true)
    private Integer toPage;

    @Column(nullable = false)
    private Integer leftSide;

    @Column(nullable = false)
    private Integer rightSide;

    @Column(nullable = false)
    private Integer topSide;

    @Column(nullable = false)
    private Integer bottomSide;

    @Column(nullable = false)
    private Boolean isOneSide;

    @Column(nullable = false)
    private Integer numOfPageOneSide;

    @ManyToOne
    @JoinColumn(name = "transaction_id", nullable = false)
    @JsonBackReference
    private PrintTransaction transaction;

    @ManyToOne
    @JoinColumn(name = "document_id", nullable = false)
    @JsonBackReference
    private Document document;

    @PrePersist
    private void setDefaultValue() {
        if (this.topSide == null) {
            this.topSide = 0;
        }
        if (this.bottomSide == null) {
            this.bottomSide = 0;
        }
        if (this.leftSide == null) {
            this.leftSide = 0;
        }
        if (this.rightSide == null) {
            this.rightSide = 0;
        }
        if (this.isOneSide == null) {
            this.isOneSide = true;
        }
        if (this.numOfCopies == null) {
            this.numOfCopies = 1;
        }
        if (this.numOfPageOneSide == null) {
            this.numOfPageOneSide = 1;
        }

    }

}
