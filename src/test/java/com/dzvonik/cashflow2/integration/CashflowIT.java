package com.dzvonik.cashflow2.integration;

import com.dzvonik.cashflow2.model.TransactionType;
import com.dzvonik.cashflow2.model.dto.TransactionDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
class CashflowIT extends DatabaseIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void createTransaction() throws Exception {
        TransactionDto dto = TransactionDto.builder()
                .amount(new BigDecimal("1032.52"))
                .type(TransactionType.INCOME)
                .date(LocalDate.of(2022, 10, 25))
                .comment("Test dto")
                .accountId(1L)
                .categoryId(3L)
                .build();

        mockMvc.perform(post("/transaction/new")
                .contentType("application/json")
                .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }

    @Test
    void getById() throws Exception {
        // (5, '250.11', 'INCOME', '2022-08-17', 'Подработка в такси', 1, 3)
        mockMvc.perform(get("/transaction/5?accountId=1")
                .contentType("application/json"))
                .andExpectAll(
                        status().isOk(),
                        header().string("Cache-control", "no-store, no-cache, must-revalidate"),
                        jsonPath("$.transaction.id").value(5),
                        jsonPath("$.transaction.amount").value(250.11),
                        jsonPath("$.transaction.type").value("INCOME"),
                        jsonPath("$.transaction.date").value("2022-08-17"),
                        jsonPath("$.transaction.comment").value("Подработка в такси"),
                        jsonPath("$.transaction.accountId").value(1),
                        jsonPath("$.transaction.categoryId").value(3)
                );
    }

    @Test
    void deleteById() throws Exception {
        mockMvc.perform(delete("/transaction/5?accountId=1&categoryId=3")
                .contentType("application/json"))
                .andExpect(status().isNoContent());
    }

}