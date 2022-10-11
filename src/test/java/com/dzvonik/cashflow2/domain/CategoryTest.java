package com.dzvonik.cashflow.domain.entity;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

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
        List<Transaction> transactions = mock();
        Category categoryWithData = new Category(3L, "Home", transactions);

        assertThat(categoryWithData.getId()).isEqualTo(3L);
        assertThat(categoryWithData.getTitle()).isEqualTo("Home");
        assertThat(categoryWithData.getTransactions()).containsExactlyInAnyOrderElementsOf(transactions);
    }

    @Test
    void toString_WhenCall_ThenReturnStringRepresentation() {
        Category categoryWithData = new Category(0L, "Home", mock());

        assertThat(categoryWithData.toString()).contains(
            "id=0",
            "title=Home"
        );
    }

    @Test
    void equalsAndHashCode() {
        EqualsVerifier.forClass(User.class)
                .suppress(Warning.SURROGATE_KEY)
                .verify();
    }

    private List<Transaction> mock() {
        return List.of(org.mockito.Mockito.mock(Transaction.class));
    }

}
