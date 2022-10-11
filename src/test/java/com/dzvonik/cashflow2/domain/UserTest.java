package com.dzvonik.cashflow.domain.entity;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

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
        EqualsVerifier.forClass(User.class)
                .suppress(Warning.SURROGATE_KEY)
                .verify();
    }

    private List<Account> mock() {
        return List.of(org.mockito.Mockito.mock(Account.class));
    }

}
