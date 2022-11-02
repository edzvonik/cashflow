package com.dzvonik.cashflow2.service;

import com.dzvonik.cashflow2.model.Transaction;
import com.dzvonik.cashflow2.model.TransactionType;
import com.dzvonik.cashflow2.model.dto.TransactionDto;
import com.dzvonik.cashflow2.repository.AccountRepository;
import com.dzvonik.cashflow2.service.impl.DefaultTransactionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
class DefaultTransactionServiceImplTest {

    @MockBean
    private AccountRepository accountRepository;

    private TransactionService transactionService = new DefaultTransactionServiceImpl(accountRepository);

    @Test
    void dtoToEntity() {
        TransactionDto dto = TransactionDto.builder()
                .amount(new BigDecimal("1032.52"))
                .type(TransactionType.INCOME)
                .date(LocalDate.of(2022, 10, 25))
                .comment("Test dto")
                .accountId(1L)
                .categoryId(3L)
                .build();

        Transaction transactionFromDto = transactionService.dtoToEntity(dto);

        assertThat(transactionFromDto.getAmount()).isEqualTo(new BigDecimal("1032.52"));
        assertThat(transactionFromDto.getType()).isEqualTo(TransactionType.INCOME);
        assertThat(transactionFromDto.getDate()).isEqualTo(LocalDate.of(2022, 10, 25));
        assertThat(transactionFromDto.getComment()).isEqualTo("Test dto");
    }

}