package com.dzvonik.cashflow.domain.entity;

import com.dzvonik.cashflow.domain.entity.enums.TransactionType;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class TransactionTest {

    @Test
    void defaultConstructor_WhenCreatedWithReflection_ThenNoExceptionThrown() {
        assertThatCode(() -> Transaction.class.getDeclaredConstructor().newInstance())
                .doesNotThrowAnyException();
    }

    @Test
    void builder_WhenSetValues_ThenReturnValues() {
        Transaction transactionWithData = Transaction.builder()
                .id(7L)
                .amount(new BigDecimal("1023.56"))
                .type(TransactionType.EXPENSE)
                .date(LocalDate.of(2022, 1, 6))
                .comment("Test!")
                .build();

        assertThat(transactionWithData.getId()).isEqualTo(7L);
        assertThat(transactionWithData.getAmount()).isEqualTo(new BigDecimal("1023.56"));
        assertThat(transactionWithData.getType()).isEqualTo(TransactionType.EXPENSE);
        assertThat(transactionWithData.getDate()).isEqualTo(LocalDate.of(2022, 1, 6));
        assertThat(transactionWithData.getComment()).isEqualTo("Test!");
    }

    @Test
    void toString_WhenCall_ThenReturnStringRepresentation() {
        Transaction transactionWithData = Transaction.builder()
                .id(0L)
                .amount(new BigDecimal("555963.12"))
                .type(TransactionType.INCOME)
                .date(LocalDate.of(2022, 12, 5))
                .build();

        assertThat(transactionWithData.toString()).contains(
                "id=0",
                "amount=555963.12",
                "type=INCOME",
                "date=2022-12-05"
        );
    }

    @Test
    void equalsAndHashCode() {
        EqualsVerifier.forClass(Transaction.class)
                .suppress(Warning.SURROGATE_KEY)
                .verify();
    }

}
