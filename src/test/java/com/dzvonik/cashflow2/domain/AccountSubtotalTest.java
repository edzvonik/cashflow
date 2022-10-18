package com.dzvonik.cashflow2.domain;

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
        LocalDateTime now = LocalDateTime.now();
        BigDecimal subtotal = new BigDecimal("105.4");

        AccountSubtotal accountSubtotal = new AccountSubtotal(1L, now, subtotal);
        assertThat(accountSubtotal.getId()).isEqualTo(1L);
        assertThat(accountSubtotal.getCalculatedAt()).isEqualTo(now);
        assertThat(accountSubtotal.getSubtotal()).isEqualTo(subtotal);
    }

    @Test
    void toString_WhenCall_ThenReturnStringRepresentation() {
        LocalDateTime now = LocalDateTime.now();
        BigDecimal subtotal = new BigDecimal("105.4");

        AccountSubtotal accountSubtotal = new AccountSubtotal(1L, now, subtotal);
        assertThat(accountSubtotal.toString()).contains(
                now.toString(),
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