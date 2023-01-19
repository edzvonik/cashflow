package com.dzvonik.cashflow2.controller;

import com.dzvonik.cashflow2.model.dto.TransactionDto;
import com.dzvonik.cashflow2.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/transactions")
public class TransactionController {

    @Qualifier("defaultTransactionService")
    private final TransactionService transactionService;

    @PostMapping("/new")
    public ResponseEntity<Object> create(@RequestBody @Valid TransactionDto dto) {
        Long transactionId = transactionService.create(dto);
        Map<String, Object> body = new HashMap<>();
        body.put("path", "/transactions/" + transactionId);
        return new ResponseEntity<>(body, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id, @RequestParam Long accountId) {
        TransactionDto transactionDto = transactionService.getById(id, accountId);

        Map<String, Object> body = new HashMap<>();
        body.put("transaction", transactionDto);

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Cache-control", "no-store, no-cache, must-revalidate");

        return new ResponseEntity<>(body, headers, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> getAll(@SortDefault(sort = "date", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<TransactionDto> transactionsDto = transactionService.getAll(pageable);

        Map<String, Object> body = new HashMap<>();
        body.put("transactions", transactionsDto.getContent());
        body.put("totalPages", transactionsDto.getTotalPages());
        body.put("totalElements", transactionsDto.getTotalElements());

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Cache-control", "no-store, no-cache, must-revalidate");

        return new ResponseEntity<>(body, headers, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable Long id, @RequestParam Long accountId, @RequestParam Long categoryId) {
        if (!transactionService.deleteById(id, accountId, categoryId)) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
