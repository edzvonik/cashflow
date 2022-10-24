package com.dzvonik.cashflow2.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class AccountSubtotalTest {

    @Test
    void defaultConstructor_WhenCreatedWithReflection_ThenNoExceptionThrown() {
        assertThatCode(() -> AccountSubtotal.class.getDeclaredConstructor().newInstance()).doesNotThrowAnyException();
    }

    @Test
    void constructor_WhenSetValues_ThenReturnValues() {
        LocalDateTime calculatedAt = LocalDateTime.of(2022, 10, 23, 23, 50);
        LocalDateTime updatedAt = LocalDateTime.now();
        BigDecimal subtotal = new BigDecimal("1553.323");

        AccountSubtotal accountSubtotal = AccountSubtotal.builder()
                .id(1L)
                .subtotal(subtotal)
                .calculatedAt(calculatedAt)
                .updatedAt(updatedAt)
                .build();
        assertThat(accountSubtotal.getId()).isEqualTo(1L);
        assertThat(accountSubtotal.getCalculatedAt()).isEqualTo(calculatedAt);
        assertThat(accountSubtotal.getUpdatedAt()).isEqualTo(updatedAt);
        assertThat(accountSubtotal.getSubtotal()).isEqualTo(subtotal);
    }

    @Test
    void toString_WhenCall_ThenReturnStringRepresentation() {
        LocalDateTime calculatedAt = LocalDateTime.of(2021, 9, 10, 9, 23);
        LocalDateTime updatedAt = LocalDateTime.of(2022, 10, 24, 10, 20);
        BigDecimal subtotal = new BigDecimal("105.4");

        AccountSubtotal accountSubtotal = AccountSubtotal.builder()
                .id(1L)
                .subtotal(subtotal)
                .calculatedAt(calculatedAt)
                .updatedAt(updatedAt)
                .build();
        assertThat(accountSubtotal.toString()).contains(
                calculatedAt.toString(),
                updatedAt.toString(),
                subtotal.toString()
        );
    }

    @Test
    void equalsAndHashCode() {
        Account account1 = Mockito.mock(Account.class);
        Account account2 = Mockito.mock(Account.class);

        EqualsVerifier.forClass(AccountSubtotal.class)
                .withPrefabValues(Account.class, account1, account2)
                .suppress(Warning.SURROGATE_KEY)
                .verify();
    }

}