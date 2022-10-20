package com.dzvonik.cashflow2.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import java.util.Collections;
import java.util.List;

@Entity
@Getter
@Builder
@EqualsAndHashCode(of = "id")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"categories", "transactions", "subtotals"})
public class Account {

    @Id
    @SequenceGenerator(name = "seq_account", sequenceName = "seq_account")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_account")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String currency;

    @JoinColumn(name = "account_id")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<AccountSubtotal> subtotals;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "account_category",
            joinColumns = {@JoinColumn(name = "account_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")}
    )
    private List<Category> categories;

    @JoinColumn(name = "account_id")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions;

    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }

    public void addTransaction(Transaction newTransaction) {
        transactions.add(newTransaction);
        int categoryIndex = categories.indexOf(newTransaction.getCategory());
        categories.get(categoryIndex).addTransaction(newTransaction);
    }

    // addCategory() {
    //  добавить категорию ко всем счетам пользователя
    // }

    // calculateIncomes
    // calculateExpenses
    // calculateTransactions
    // calculateTransactionsByCategory

    // Работа с subtotal
    // addTransaction(Long categoryId, Transaction t)
    // editTransaction(Long categoryId, Transaction newT)
    // removeTransaction(Long transactionId)


}
