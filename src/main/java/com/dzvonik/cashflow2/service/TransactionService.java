package com.dzvonik.cashflow2.service;

import com.dzvonik.cashflow2.model.Transaction;
import com.dzvonik.cashflow2.model.dto.TransactionDto;

public interface TransactionService {

    void addTransaction(TransactionDto dto);

    Transaction dtoToEntity(TransactionDto dto);

}
