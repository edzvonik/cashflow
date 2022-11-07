package com.dzvonik.cashflow2.model;

import com.dzvonik.cashflow2.exception.ResourceNotFoundException;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountTest {

    @Test
    void defaultConstructor_WhenCreatedWithReflection_ThenNoExceptionThrown() {
        assertThatCode(() -> Account.class.getDeclaredConstructor().newInstance())
                .doesNotThrowAnyException();
    }

    @Test
    void builder_WhenSetValues_ThenReturnValues() {
        List<Transaction> transactions = mockList(Transaction.class);
        List<Category> categories = mockList(Category.class);

        Account accountWithData = Account.builder()
                .id(5L)
                .title("Cash")
                .currency("USD")
                .categories(categories)
                .transactions(transactions)
                .build();

        assertThat(accountWithData.getId()).isEqualTo(5L);
        assertThat(accountWithData.getTitle()).isEqualTo("Cash");
        assertThat(accountWithData.getCurrency()).isEqualTo("USD");
        assertThat(accountWithData.getCategories()).containsExactlyInAnyOrderElementsOf(categories);
        assertThat(accountWithData.getTransactions()).containsExactlyInAnyOrderElementsOf(transactions);
    }

    @Test
    void toString_WhenCall_ThenReturnStringRepresentation() {
        Account accountWithData = Account.builder()
                .id(0L)
                .title("Card")
                .currency("RUB")
                .build();

        assertThat(accountWithData.toString()).contains(
                "id=0",
                "title=Card",
                "currency=RUB"
        );
    }

    @Test
    void equalsAndHashCode() {
        EqualsVerifier.forClass(User.class)
                .suppress(Warning.SURROGATE_KEY)
                .verify();
    }

    @Test
    void getTransactionById_WhenCall_ThenReturnTransaction() {
        Transaction transaction = Transaction.builder()
                .id(5L)
                .amount(new BigDecimal("14.1"))
                .type(TransactionType.INCOME)
                .date(LocalDate.of(2022, 11, 2))
                .comment("Test comment")
                .accountId(1L)
                .categoryId(2L)
                .build();
        List<Transaction> transactions = List.of(transaction);
        Account account = Account.builder()
                .id(1L)
                .transactions(transactions)
                .categories(mockList(Category.class))
                .build();

        Transaction receivedTransaction = account.getTransactionById(5L);

        assertThat(receivedTransaction.getId()).isEqualTo(5L);
        assertThat(receivedTransaction.getAmount()).isEqualTo(new BigDecimal("14.1"));
        assertThat(receivedTransaction.getType()).isEqualTo(TransactionType.INCOME);
        assertThat(receivedTransaction.getDate()).isEqualTo(LocalDate.of(2022, 11, 2));
        assertThat(receivedTransaction.getComment()).isEqualTo("Test comment");
    }

    @Test
    void getTransactionById_WhenNotExist_ThenThrowException() {
        Transaction transaction = Transaction.builder()
                .id(5L)
                .amount(new BigDecimal("14.1"))
                .type(TransactionType.INCOME)
                .date(LocalDate.of(2022, 11, 2))
                .comment("Test comment")
                .accountId(1L)
                .categoryId(2L)
                .build();
        List<Transaction> transactions = List.of(transaction);
        Account account = Account.builder()
                .id(1L)
                .transactions(transactions)
                .categories(mockList(Category.class))
                .build();

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            account.getTransactionById(1L);
        });
    }

    @Test
    void getTransactions_WhenAddThroughGetter_ThenReturnUnmodifiableList() {
        Account account = Account.builder()
                .id(1L)
                .title("Cash")
                .transactions(new ArrayList<>(mockList(Transaction.class)))
                .categories(new ArrayList<>(mockList(Category.class)))
                .build();

        Exception exception = assertThrows(UnsupportedOperationException.class, () -> {
            account.getTransactions().add(Mockito.mock(Transaction.class));
        });
    }

    @Test
    void getCategoryById_WhenNotExist_ThenResourceNotFoundException() {
        Account account = Account.builder()
                .id(1L)
                .title("Cash")
                .currency("RUB")
                .categories(mockList(Category.class))
                .transactions(mockList(Transaction.class))
                .build();

        RuntimeException exception = assertThrows(ResourceNotFoundException.class, () -> {
            account.getCategoryById(5L);
        });
    }

    @Test
    void getCategoryById_WhenCall_ThenReturnCategory() {
        Category category = new Category(1L, "Test_Category", mockList(Transaction.class));
        Account account = Account.builder()
                .id(1L)
                .title("Cash")
                .currency("RUB")
                .categories(new ArrayList<>(List.of(category)))
                .transactions(mockList(Transaction.class))
                .build();

        assertThat(account.getCategoryById(1L).getTitle()).isEqualTo("Test_Category");
    }

    @Test
    void addTransaction_WhenCall_ThenAddTransactionToTransactionAndCategory() {
        Category category = new Category(1L, "Test_Category", mockList(Transaction.class));
        Account account = Account.builder()
                .id(1L)
                .title("Cash")
                .currency("RUB")
                .categories(new ArrayList<>(List.of(category)))
                .transactions(mockList(Transaction.class))
                .build();

        Transaction transaction = Transaction.builder()
                .id(4L)
                .amount(new BigDecimal(403.2))
                .type(TransactionType.INCOME)
                .date(LocalDate.of(2022, 10, 24))
                .comment("Test transaction")
                .build();

        account.addTransaction(transaction, 1L);

        assertThat(account.getTransactions().contains(transaction)).isTrue();
        assertThat(account.getCategoryById(category.getId()).getTransactions().contains(transaction)).isTrue();
    }

    @Test
    void removeTransactionById_WhenExist_ThenReturnTrue() {
        Transaction transaction = Transaction.builder()
                .id(5L)
                .amount(new BigDecimal("33.22"))
                .type(TransactionType.INCOME)
                .date(LocalDate.of(2022, 11, 4))
                .comment("Remove transaction test")
                .accountId(5L)
                .categoryId(3L)
                .build();
        List<Transaction> transactions = new ArrayList<>(List.of(transaction));
        Category testCategory = new Category(3L, "Home", transactions);
        List<Category> categories = new ArrayList<>(List.of(testCategory));
        Account testAccount = Account.builder()
                .id(5L)
                .categories(categories)
                .transactions(transactions)
                .build();

        assertThat(testAccount.getTransactions()).doesNotContain(transaction);
        assertThat(testAccount.getCategoryById(3L).getTransactions()).doesNotContain(transaction);
    }

    @Test
    void removeTransactionById_WhenNotExist_ThenThrowsResourceNotFound() {
        List<Transaction> transactions = new ArrayList<>(List.of());
        List<Category> categories = new ArrayList<>(List.of());
        Account testAccount = Account.builder()
                .id(5L)
                .categories(categories)
                .transactions(transactions)
                .build();

        RuntimeException exception = assertThrows(ResourceNotFoundException.class, () -> {
            testAccount.removeTransactionById(5L, 3L);
        });
    }

    private <T> List<T> mockList(Class<T> c) {
        return new ArrayList<>(List.of(org.mockito.Mockito.mock(c)));
    }

}
