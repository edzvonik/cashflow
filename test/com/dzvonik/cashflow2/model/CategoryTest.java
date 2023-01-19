package com.dzvonik.cashflow2.model;

import com.dzvonik.cashflow2.exception.ResourceNotFoundException;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CategoryTest {

    @Test
    void defaultConstructor_WhenCreatedWithReflection_ThenNoExceptionThrown() {
        assertThatCode(() -> Category.class.getDeclaredConstructor().newInstance())
                .doesNotThrowAnyException();
    }

    @Test
    void constructor_WhenSetValues_ThenReturnValues() {
        List<Transaction> transactions = mockList();
        Category categoryWithData = new Category(3L, "Home", transactions);

        assertThat(categoryWithData.getId()).isEqualTo(3L);
        assertThat(categoryWithData.getTitle()).isEqualTo("Home");
        assertThat(categoryWithData.getTransactions()).containsExactlyInAnyOrderElementsOf(transactions);
    }

    @Test
    void toString_WhenCall_ThenReturnStringRepresentation() {
        Category categoryWithData = new Category(0L, "Home", mockList());

        assertThat(categoryWithData.toString()).contains(
                "id=0",
                "title=Home"
        );
    }

    // TODO: Refactor
    @Test
    void equalsAndHashCode() {
        EqualsVerifier.forClass(User.class)
                .suppress(Warning.SURROGATE_KEY)
                .verify();
    }

    @Test
    void getTransactionById_WhenCall_ThenReturnTransaction() {
        Transaction expectedTransaction = Transaction.builder()
                .id(5L)
                .amount(new BigDecimal("33.22"))
                .type(TransactionType.INCOME)
                .date(LocalDate.of(2022, 11, 4))
                .comment("Remove transaction test")
                .accountId(5L)
                .categoryId(5L)
                .build();
        List<Transaction> transactions = new ArrayList<>(List.of(expectedTransaction));
        Category testCategory = new Category(3L, "Home", transactions);

        Transaction actualTransaction = testCategory.getTransactionById(5L);

        assertThat(actualTransaction).usingRecursiveComparison().isEqualTo(actualTransaction);
    }

    @Test
    void getTransactionById_WhenNotExist_ThenThrowsResourceNotFound() {
        List<Transaction> transactions = new ArrayList<>(List.of());
        Category testCategory = new Category(3L, "Home", transactions);

        RuntimeException exception = assertThrows(ResourceNotFoundException.class, () -> {
            testCategory.getTransactionById(5L);
        });
        assertThat(exception.getMessage()).isEqualTo("Transaction with id=5 not found");
    }

    @Test
    void deleteTransaction_WhenExist_ThenReturnTrue() {
        Transaction transaction = Transaction.builder()
                .id(5L)
                .amount(new BigDecimal("33.22"))
                .type(TransactionType.INCOME)
                .date(LocalDate.of(2022, 11, 4))
                .comment("Remove transaction test")
                .accountId(5L)
                .categoryId(5L)
                .build();
        List<Transaction> transactions = new ArrayList<>(List.of(transaction));
        Category testCategory = new Category(3L, "Home", transactions);

        boolean isDeleted = testCategory.deleteTransactionById(5L);

        assertThat(isDeleted).isTrue();
        assertThat(testCategory.getTransactions()).doesNotContain(transaction);
    }

    @Test
    void deleteTransaction_WhenNotExist_ThenThrowsResourceNotFound() {
        List<Transaction> transactions = new ArrayList<>();
        Category testCategory = new Category(3L, "Home", transactions);

        RuntimeException exception = assertThrows(ResourceNotFoundException.class, () -> {
            testCategory.getTransactionById(5L);
        });
        assertThat(exception.getMessage()).isEqualTo("Transaction with id=5 not found");
    }

    private List<Transaction> mockList() {
        return List.of(org.mockito.Mockito.mock(Transaction.class));
    }

}
