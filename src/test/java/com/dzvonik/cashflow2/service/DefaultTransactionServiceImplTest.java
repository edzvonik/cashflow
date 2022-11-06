package com.dzvonik.cashflow2.service;

import com.dzvonik.cashflow2.model.Account;
import com.dzvonik.cashflow2.model.Category;
import com.dzvonik.cashflow2.model.Transaction;
import com.dzvonik.cashflow2.model.TransactionType;
import com.dzvonik.cashflow2.model.dto.TransactionDto;
import com.dzvonik.cashflow2.repository.AccountRepository;
import com.dzvonik.cashflow2.repository.TransactionRepository;
import com.dzvonik.cashflow2.service.impl.DefaultTransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Mock
    private TransactionRepository transactionRepository;

    private TransactionService transactionService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        transactionService = Mockito.spy(new DefaultTransactionServiceImpl(accountRepository, transactionRepository));
    }

    @Test
    void create_WhenCreate_ThenReturnTransactionId() throws Exception {
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

        assertThat(dto).usingRecursiveComparison().isEqualTo(transaction);
    }

    @Test
    void getAll_WhenCall_ThenReturnTransactionDtoPage() {
        Transaction transaction = Transaction.builder()
                .id(2L)
                .amount(new BigDecimal("55125.51"))
                .type(TransactionType.INCOME)
                .date(LocalDate.of(2021, 10, 3))
                .comment("Get transaction test")
                .accountId(7L)
                .categoryId(11L)
                .build();
        List<Transaction> content = new ArrayList<>(List.of(transaction));
        Pageable pageable = PageRequest.of(0, 3);
        Page<Transaction> transactionPage = new PageImpl<>(content, pageable, 1);

        when(transactionRepository.findAll(pageable)).thenReturn(transactionPage);
        Page<TransactionDto> transactionDtoPage = transactionService.getAll(pageable);
        TransactionDto dtoFromPage = transactionDtoPage.getContent().get(0);

        assertThat(transactionDtoPage).usingRecursiveComparison().isEqualTo(transactionPage);
        assertThat(dtoFromPage).usingRecursiveComparison().isEqualTo(transaction);
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

        Transaction transaction = transactionService.dtoToEntity(dto);

        assertThat(transaction).usingRecursiveComparison().isEqualTo(dto);
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

        TransactionDto dto = transactionService.entityToDto(transaction);

        assertThat(dto).usingRecursiveComparison().isEqualTo(transaction);
    }

}