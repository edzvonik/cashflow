package com.dzvonik.cashflow2.model;

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
        List<Transaction> transactions = mock(Transaction.class);
        List<Category> categories = mock(Category.class);

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
            "currency=RUB",
            "balance=0.00"
        );
    }

    @Test
    void equalsAndHashCode() {
        Transaction transaction1 = Mockito.mock(Transaction.class);
        Transaction transaction2 = Mockito.mock(Transaction.class);
        Account account1 = Mockito.mock(Account.class);
        Account account2 = Mockito.mock(Account.class);
        Category category1 = Mockito.mock(Category.class);
        Category category2 = Mockito.mock(Category.class);

        EqualsVerifier.forClass(User.class)
                .withPrefabValues(Transaction.class, transaction1, transaction2)
                .withPrefabValues(Account.class, account1, account2)
                .withPrefabValues(Category.class, category1, category2)
                .suppress(Warning.SURROGATE_KEY)
                .verify();
    }

    @Test
    void getTransactions_WhenAddThroughGetter_ThenThrowException() {
        Account account = Account.builder()
                .id(1L)
                .title("Cash")
                .transactions(new ArrayList<>(mock(Transaction.class)))
                .categories(new ArrayList<>(mock(Category.class)))
                .build();

        Exception exception = assertThrows(UnsupportedOperationException.class, () -> {
            account.getTransactions().add(Mockito.mock(Transaction.class));
        });
    }

    @Test
    void getCategoryById_WhenCall_ThenReturnCategory() {
        Category category = new Category(1L, "Test_Category", mock(Transaction.class));
        Account account = Account.builder()
                .id(1L)
                .title("Cash")
                .currency("RUB")
                .lastSubtotal(Mockito.mock(AccountSubtotal.class))
                .categories(new ArrayList<>(List.of(category)))
                .transactions(mock(Transaction.class))
                .build();

        assertThat(account.getCategoryById(1L).getTitle()).isEqualTo("Test_Category");
    }

    @Test
    void addTransaction_WhenCall_ThenAddTransactionToTransactionAndCategory() {
        Category category = new Category(1L, "Test_Category", mock(Transaction.class));
        Account account = Account.builder()
                .id(1L)
                .title("Cash")
                .currency("RUB")
                .lastSubtotal(Mockito.mock(AccountSubtotal.class))
                .categories(new ArrayList<>(List.of(category)))
                .transactions(mock(Transaction.class))
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

    private <T> List<T> mock(Class<T> c) {
        return new ArrayList<>(List.of(org.mockito.Mockito.mock(c)));
    }

}