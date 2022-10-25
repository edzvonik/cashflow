package com.dzvonik.cashflow2.integration;

import com.dzvonik.cashflow2.model.Account;
import com.dzvonik.cashflow2.model.Category;
import com.dzvonik.cashflow2.model.Transaction;
import com.dzvonik.cashflow2.model.TransactionType;
import com.dzvonik.cashflow2.model.dto.TransactionDto;
import com.dzvonik.cashflow2.repository.AccountRepository;
import com.dzvonik.cashflow2.service.TransactionService;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@AllArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class TransactionServiceImplIT extends DatabaseIT {

    private final AccountRepository accountRepository;

    private final TransactionService transactionService;

    @Test
    void addTransaction_WhenCall_ThenFindsById() {
        TransactionDto dto = TransactionDto.builder()
                .amount(new BigDecimal("1032.52"))
                .type(TransactionType.INCOME)
                .date(LocalDate.of(2022, 10, 25))
                .comment("Test dto")
                .accountId(1L)
                .categoryId(3L)
                .build();

        Transaction transaction = transactionService.dtoToEntity(dto);
        transactionService.addTransaction(dto);
        Account account = accountRepository.findById(1L).get();
        Category category = account.getCategoryById(3L);

        assertThat(account.getTransactions().contains(transaction)).isTrue();
        assertThat(category.getTransactions().contains(transaction)).isTrue();
    }
}