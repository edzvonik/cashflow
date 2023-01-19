package com.dzvonik.cashflow2.model;

import com.dzvonik.cashflow2.exception.ResourceNotFoundException;
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
@ToString(exclude = {"categories", "transactions"})
public class Account {

    @Id
    @SequenceGenerator(name = "seq_account", sequenceName = "seq_account")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_account")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String currency;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "account_category",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;

    @JoinColumn(name = "account_id")
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Transaction> transactions;

    public Transaction getTransactionById(Long id) {
        return transactions.stream().filter(t -> t.getId().equals(id)).findFirst().orElseThrow(() -> new ResourceNotFoundException("Transaction with id=" + id + " not found"));
    }

    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }

    public void addTransaction(Transaction newTransaction, Long categoryId) {
        transactions.add(newTransaction);
        getCategoryById(categoryId).addTransaction(newTransaction);
    }

    public boolean deleteTransactionById(Long id, Long categoryId) {
        Transaction transaction = getTransactionById(id);
        boolean isDeletedFromAccount = transactions.remove(transaction);
        boolean fromCategory = getCategoryById(categoryId).deleteTransactionById(id);
        return isDeletedFromAccount && fromCategory;
    }

    public Category getCategoryById(Long id) {
        return categories.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Category with id=" + id + " not found"));
    }

}
