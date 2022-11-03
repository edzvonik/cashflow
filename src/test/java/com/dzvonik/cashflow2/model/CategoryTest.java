package com.dzvonik.cashflow2.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

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

    private List<Transaction> mockList() {
        return List.of(org.mockito.Mockito.mock(Transaction.class));
    }

}
