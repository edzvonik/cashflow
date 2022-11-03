package com.dzvonik.cashflow2.service;

import com.dzvonik.cashflow2.model.Account;
import com.dzvonik.cashflow2.model.Category;
import com.dzvonik.cashflow2.model.Transaction;
import com.dzvonik.cashflow2.model.TransactionType;
import com.dzvonik.cashflow2.model.dto.TransactionDto;
import com.dzvonik.cashflow2.repository.AccountRepository;
import com.dzvonik.cashflow2.service.impl.DefaultTransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultTransactionServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    // @Spy
    private TransactionService transactionService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        transactionService = Mockito.spy(new DefaultTransactionServiceImpl(accountRepository));
    }

    @Test
    void create() throws Exception {
        TransactionDto dto = TransactionDto.builder()
                .amount(new BigDecimal("43235.12"))
                .type(TransactionType.INCOME)
                .date(LocalDate.of(2021, 9, 5))
                .comment("Income test transaction")
                .accountId(1L)
                .categoryId(2L)
                .build();
        List<Transaction> transactions = new ArrayList<>(List.of());
        List<Category> categories = new ArrayList<>(List.of(new Category(2L, "Home", transactions)));
        Account account = Account.builder()
                .id(1L)
                .transactions(transactions)
                .categories(categories)
                .build();

        Transaction transaction = transactionService.dtoToEntity(dto);
        when(transactionService.dtoToEntity(dto)).thenReturn(transaction);
        when(accountRepository.findById(account.getId())).thenReturn(Optional.of(account));
        when(accountRepository.saveAndFlush(account)).thenAnswer(invocation -> {
            ReflectionTestUtils.setField(transaction, "id", 58L);
            return null;
        });
        Long transactionId = transactionService.create(dto);
        
        assertThat(transactionId).isNotNull();
        verify(accountRepository).findById(account.getId());
        verify(accountRepository).saveAndFlush(account);
    }

    @Test
    void getById_WhenCall_ThenReturnTransactionDto() {
        Transaction transaction = Transaction.builder()
                .id(57L)
                .amount(new BigDecimal("43235.12"))
                .type(TransactionType.INCOME)
                .date(LocalDate.of(2021, 9, 5))
                .comment("Income test transaction")
                .accountId(1L)
                .categoryId(2L)
                .build();
        List<Transaction> transactions = new ArrayList<>(List.of(transaction));
        Account account = Account.builder()
                .id(1L)
                .transactions(transactions)
                .build();

        when(accountRepository.findById(1L)).thenReturn(Optional.ofNullable(account));
        TransactionDto dto = transactionService.getById(57L, 1L);

        assertThat(dto.getAmount()).isEqualTo(new BigDecimal("43235.12"));
        assertThat(dto.getType()).isEqualTo(TransactionType.INCOME);
        assertThat(dto.getDate()).isEqualTo(LocalDate.of(2021, 9, 5));
        assertThat(dto.getComment()).isEqualTo("Income test transaction");
        assertThat(dto.getAccountId()).isEqualTo(1L);
        assertThat(dto.getCategoryId()).isEqualTo(2L);
    }

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

    @Test
    void entityToDto() {
        Transaction transaction = Transaction.builder()
                .id(55L)
                .amount(new BigDecimal("1.03"))
                .type(TransactionType.EXPENSE)
                .date(LocalDate.of(2021, 9, 5))
                .comment("Expense transaction")
                .accountId(1L)
                .categoryId(2L)
                .build();

        TransactionDto dtoFromTransaction = transactionService.entityToDto(transaction);
        assertThat(dtoFromTransaction.getAmount()).isEqualTo(new BigDecimal("1.03"));
        assertThat(dtoFromTransaction.getType()).isEqualTo(TransactionType.EXPENSE);
        assertThat(dtoFromTransaction.getDate()).isEqualTo(LocalDate.of(2021, 9, 5));
        assertThat(dtoFromTransaction.getComment()).isEqualTo("Expense transaction");
        assertThat(dtoFromTransaction.getAccountId()).isEqualTo(1L);
        assertThat(dtoFromTransaction.getCategoryId()).isEqualTo(2L);
    }

}