package com.dzvonik.cashflow.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int transaction_id;
    private LocalDateTime created_at;
    private BigDecimal amount;
    private String comment;

    public Transaction() {
    }

    public Transaction(LocalDateTime created_at, BigDecimal amount, String comment) {
        this.created_at = created_at;
        this.amount = amount;
        this.comment = comment;
    }

    public int getTransaction_id() {
        return transaction_id;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getComment() {
        return comment;
    }
}
