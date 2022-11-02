package com.dzvonik.cashflow2.service;

import com.dzvonik.cashflow2.model.Transaction;
import com.dzvonik.cashflow2.model.dto.TransactionDto;

public interface TransactionService {

    Long create(TransactionDto dto);

    TransactionDto getById(Long id);

    Transaction dtoToEntity(TransactionDto dto);

}
