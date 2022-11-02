package com.dzvonik.cashflow2.controller;

import com.dzvonik.cashflow2.model.dto.TransactionDto;
import com.dzvonik.cashflow2.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/transaction")
public class TransactionController {

    @Qualifier("defaultTransactionService")
    private final TransactionService transactionService;

    @PostMapping("/new")
    public ResponseEntity<Object> create(@RequestBody @Valid TransactionDto dto) {
        Long transactionId = transactionService.create(dto);
        Map<String, Object> body = new HashMap<>();
        body.put("path", "/transaction/" + transactionId);
        return new ResponseEntity<>(body, HttpStatus.CREATED);
    }

}
