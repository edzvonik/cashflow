package com.dzvonik.cashflow2.model.dto;

import com.dzvonik.cashflow2.model.TransactionType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TransactionDto {

    private final Long id;

    @NotNull(message = "Amount may not be null")
    private final BigDecimal amount;

    @NotNull(message = "Type may not be null")
    private final TransactionType type;

    @NotNull(message = "Date may not be null")
    private final LocalDate date;

    private final String comment;

    @NotNull(message = "Account may not be null")
    private final Long accountId;

    @NotNull(message = "Category may not be null")
    private final Long categoryId;

}
