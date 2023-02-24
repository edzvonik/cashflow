package com.dzvonik.cashflow.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int transaction_id;
    private BigDecimal amount;
    private String comment;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate created_at;

    public Transaction() {
    }

    public Transaction(LocalDate created_at, BigDecimal amount, String comment) {
        this.created_at = created_at;
        this.amount = amount;
        this.comment = comment;
    }

    public int getTransaction_id() {
        return transaction_id;
    }

    public LocalDate getCreated_at() {
        return created_at;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getComment() {
        return comment;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setCreated_at(LocalDate created_at) {
        this.created_at = created_at;
    }

}
