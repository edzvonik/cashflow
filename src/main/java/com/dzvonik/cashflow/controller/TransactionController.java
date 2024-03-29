package com.dzvonik.cashflow.controller;

import com.dzvonik.cashflow.model.Transaction;
import com.dzvonik.cashflow.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("transactions")
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping
    public String getAll(Model model) {
        List<Transaction> transactions = transactionRepository.findAll();
        model.addAttribute("transactions", transactions);
        model.addAttribute("transaction", new Transaction());
        return "transactions";
    }

    @PostMapping("/new")
    public String createNew(@ModelAttribute("transaction") Transaction transaction) {
        // TODO: Добавить транзакцию
        transactionRepository.save(transaction);
        return "redirect:/transactions";
    }

}
