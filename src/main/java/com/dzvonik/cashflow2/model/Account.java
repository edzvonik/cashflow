package com.dzvonik.cashflow2.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.JoinFormula;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import java.util.Collections;
import java.util.Comparator;
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "account_category",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;
    /*
    Счета приходят только с теми категориями, которые используются в транзакциях
    Но если я хочу использовать категорию, которой нет в счете?
    Все категории будут выводиться по счетам - findAllByAccountIds только уникальные
    Когда добавляешь транзакцию - будут видны все категории
     */

    @JoinColumn(name = "account_id")
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Transaction> transactions;

    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }

    public void addTransaction(Transaction newTransaction, Long categoryId) {
        transactions.add(newTransaction);
        getCategoryById(categoryId).addTransaction(newTransaction);
    }

    public Category getCategoryById(Long id) {
        return categories.stream().filter(c -> c.getId().equals(id)).findFirst().get();
    }



    /*
    void addTransaction(Transaction newTransaction) {
        0. getAccountByUserIdAndId(c категориями) - Получить счета по user_id, user_id через UserService.getLoggedUser
        1. dtoToEntity (сервис) с подключением account и category
        2. account.addTransaction(newTransactionEntity) - добавляет в account.transactions и category.transactions
        3. accountRepo.save(account);
        // 1. сохранить в transactions
        // 2. сохранить в category->transactions
        getCategoryById(newTransaction.getCategory()).addTransaction(new
    }


    */


    // calculateIncomes
    // calculateExpenses
    // calculateTransactions
    // calculateTransactionsByCategory

    // Работа с subtotal
    // addTransaction(Long categoryId, Transaction t)
    // editTransaction(Long categoryId, Transaction newT)
    // removeTransaction(Long transactionId)


}
