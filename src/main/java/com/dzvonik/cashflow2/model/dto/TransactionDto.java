package com.dzvonik.cashflow2.model.dto;

import com.dzvonik.cashflow2.model.TransactionType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TransactionDto {

    private final BigDecimal amount;
    private final TransactionType type;
    private LocalDate date;
    private String comment;
    private final Long accountId;
    private final Long categoryId;

}
