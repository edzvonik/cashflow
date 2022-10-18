package com.dzvonik.cashflow2.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

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
        List<AccountSubtotal> subtotals = mock(AccountSubtotal.class);

        Account accountWithData = Account.builder()
                .id(5L)
                .title("Cash")
                .currency("USD")
                .subtotals(subtotals)
                .categories(categories)
                .transactions(transactions)
                .build();

        assertThat(accountWithData.getId()).isEqualTo(5L);
        assertThat(accountWithData.getTitle()).isEqualTo("Cash");
        assertThat(accountWithData.getCurrency()).isEqualTo("USD");
        assertThat(accountWithData.getSubtotals()).containsExactlyInAnyOrderElementsOf(subtotals);
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

    private <T> List<T> mock(Class<T> c) {
        return List.of(org.mockito.Mockito.mock(c));
    }

}
