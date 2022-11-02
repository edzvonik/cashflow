package com.dzvonik.cashflow2.controller;

import com.dzvonik.cashflow2.exception.ResourceNotFoundException;
import com.dzvonik.cashflow2.model.TransactionType;
import com.dzvonik.cashflow2.model.dto.TransactionDto;
import com.dzvonik.cashflow2.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean(name = "defaultTransactionService")
    private TransactionService transactionService;

    @Test
    void create_WhenCreate_ThenReturnLocationAndCreatedStatus() throws Exception {
        TransactionDto dto = TransactionDto.builder()
                .amount(new BigDecimal("1032.52"))
                .type(TransactionType.INCOME)
                .date(LocalDate.of(2022, 10, 25))
                .comment("Test dto")
                .accountId(5L)
                .categoryId(3L)
                .build();

        when(transactionService.create(any(TransactionDto.class))).thenReturn(1L);

        mockMvc.perform(post("/transaction/new")
                .contentType("application/json")
                .content(mapper.writeValueAsString(dto)))
                .andExpectAll(
                        status().isCreated(),
                        jsonPath("$.path").value("/transaction/1")
                );
    }

    @Test
    void create_WhenInvalidIdentifier_ThenResourceNotFoundException() throws Exception {
        TransactionDto dto = TransactionDto.builder()
                .amount(new BigDecimal("1032.52"))
                .type(TransactionType.INCOME)
                .date(LocalDate.of(2022, 10, 25))
                .comment("Test dto")
                .accountId(5L)
                .categoryId(3L)
                .build();

        when(transactionService.create(any(TransactionDto.class))).thenThrow(new ResourceNotFoundException("Account with id=5 not found"));

        mockMvc.perform(post("/transaction/new")
                .contentType("application/json")
                .content(mapper.writeValueAsString(dto)))
                .andExpectAll(
                        status().isNotFound(),
                        jsonPath("$.message").value("Account with id=5 not found"),
                        jsonPath("$.uri").value("/transaction/new")
                );
    }

    @Test
    void create_WhenFieldsEmpty_ThenCorrectResponse() throws Exception {
        TransactionDto dto = TransactionDto.builder().build();

        mockMvc.perform(post("/transaction/new")
                .contentType("application/json")
                .content(mapper.writeValueAsString(dto)))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.message").value("Required fields must be filled"),
                        jsonPath("$.errors.amount").value("Amount may not be null"),
                        jsonPath("$.errors.date").value("Date may not be null"),
                        jsonPath("$.errors.type").value("Type may not be null"),
                        jsonPath("$.errors.accountId").value("Account may not be null"),
                        jsonPath("$.errors.categoryId").value("Category may not be null")
                );
    }

    @Test
    void getById_WhenCall_ThenReturnTransaction() throws Exception {
        TransactionDto dto = TransactionDto.builder()
                .id(22L)
                .amount(new BigDecimal("55512125.51"))
                .type(TransactionType.EXPENSE)
                .date(LocalDate.of(2022, 11, 2))
                .comment("Get transaction test")
                .accountId(5L)
                .categoryId(3L)
                .build();

        when(transactionService.getById(22L)).thenReturn(dto);

        mockMvc.perform(get("/transaction/22")
                .contentType("application/json"))
                .andExpectAll(
                        status().isOk(),
                        header().string("Cache-control", "no-store, no-cache, must-revalidate"),
                        jsonPath("$.transaction.id").value(22),
                        jsonPath("$.transaction.amount").value(55512125.51),
                        jsonPath("$.transaction.type").value("EXPENSE"),
                        jsonPath("$.transaction.date").value("2022-11-02"),
                        jsonPath("$.transaction.comment").value("Get transaction test"),
                        jsonPath("$.transaction.accountId").value(5),
                        jsonPath("$.transaction.categoryId").value(3)
                );
    }

}