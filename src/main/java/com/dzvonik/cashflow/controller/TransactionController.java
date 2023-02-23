package com.dzvonik.cashflow.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("transactions")
public class TransactionController {

    @GetMapping
    public String getAll(Model model) {
        List<String> transactions = new ArrayList<>(List.of("Transaction 1", "Transaction 2"));
        model.addAttribute("transactions", transactions);
        return "transactions";
    }

}
