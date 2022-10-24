package com.dzvonik.cashflow2.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class UserTest {

    @Test
    void defaultConstructor_WhenCreatedWithReflection_ThenNoExceptionThrown() {
        assertThatCode(() -> User.class.getDeclaredConstructor().newInstance())
                .doesNotThrowAnyException();
    }

    @Test
    void builder_WhenSetValues_ThenReturnValues() {
        List<Account> accounts = mock();
        User userWithData = User.builder()
                .id(7L)
                .name("Test1")
                .email("test@email.com")
                .baseCurrency("USD")
                .accounts(accounts)
                .build();

        assertThat(userWithData.getId()).isEqualTo(7L);
        assertThat(userWithData.getName()).isEqualTo("Test1");
        assertThat(userWithData.getEmail()).isEqualTo("test@email.com");
        assertThat(userWithData.getBaseCurrency()).isEqualTo("USD");
        assertThat(userWithData.getAccounts()).containsExactlyInAnyOrderElementsOf(accounts);
    }

    @Test
    void toString_WhenCall_ThenReturnStringRepresentation() {
        User userWithData = User.builder()
                .id(0L)
                .name("Test2")
                .email("test@email.com")
                .baseCurrency("RUB")
                .build();

        assertThat(userWithData.toString()).contains(
                "id=0",
                "name=Test2",
                "email=test@email.com"
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

    private List<Account> mock() {
        return List.of(org.mockito.Mockito.mock(Account.class));
    }

}
