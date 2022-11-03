package com.dzvonik.cashflow2.service.impl;

import com.dzvonik.cashflow2.exception.ResourceNotFoundException;
import com.dzvonik.cashflow2.model.Account;
import com.dzvonik.cashflow2.model.Transaction;
import com.dzvonik.cashflow2.model.dto.TransactionDto;
import com.dzvonik.cashflow2.repository.AccountRepository;
import com.dzvonik.cashflow2.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@AllArgsConstructor
@Service("defaultTransactionService")
public class DefaultTransactionServiceImpl implements TransactionService {

    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public Long create(TransactionDto dto) {
        Transaction transaction = dtoToEntity(dto);
        Account account = accountRepository
                .findById(dto.getAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("Account with " + dto.getAccountId() + " not found"));
        account.addTransaction(transaction, dto.getCategoryId());
        accountRepository.saveAndFlush(account);
        return transaction.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public TransactionDto getById(Long id, Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(
                () -> new ResourceNotFoundException("Account with " + accountId + " not found")
        );
        return entityToDto(account.getTransactionById(id));
    }

    @Override
    public Transaction dtoToEntity(TransactionDto dto) {
        return Transaction.builder()
                .accountId(dto.getAccountId())
                .categoryId(dto.getCategoryId())
                .amount(dto.getAmount())
                .type(dto.getType())
                .date(dto.getDate())
                .comment(dto.getComment())
                .build();
    }

    @Override
    public TransactionDto entityToDto(Transaction transaction) {
        return TransactionDto.builder()
                .id(transaction.getId())
                .amount(transaction.getAmount())
                .type(transaction.getType())
                .date(transaction.getDate())
                .comment(transaction.getComment())
                .accountId(transaction.getAccountId())
                .categoryId(transaction.getCategoryId())
                .build();
    }
}