package com.dzvonik.cashflow2.service.impl;

import com.dzvonik.cashflow2.model.Account;
import com.dzvonik.cashflow2.model.Transaction;
import com.dzvonik.cashflow2.model.dto.TransactionDto;
import com.dzvonik.cashflow2.repository.AccountRepository;
import com.dzvonik.cashflow2.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public void addTransaction(TransactionDto dto) {
        Account account = accountRepository.findById(dto.getAccountId()).get();
        account.addTransaction(dtoToEntity(dto), dto.getCategoryId());
    }

    @Override
    public Transaction dtoToEntity(TransactionDto dto) {
        return Transaction.builder()
                .amount(dto.getAmount())
                .type(dto.getType())
                .date(dto.getDate())
                .comment(dto.getComment())
                .build();
    }

}