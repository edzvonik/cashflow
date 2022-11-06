package com.dzvonik.cashflow2.service;

import com.dzvonik.cashflow2.model.Transaction;
import com.dzvonik.cashflow2.model.dto.TransactionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TransactionService {

    Long create(TransactionDto dto);
    TransactionDto getById(Long id, Long accountId);
    Page<TransactionDto> getAll(Pageable pageable);
    Transaction dtoToEntity(TransactionDto dto);
    TransactionDto entityToDto(Transaction transaction);

}
