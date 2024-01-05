package com.STTFV.laundryAPI.services;


import com.STTFV.laundryAPI.dto.requests.CustomerRequest;
import com.STTFV.laundryAPI.dto.requests.ExpenseRequest;
import com.STTFV.laundryAPI.entities.Expense;
import com.STTFV.laundryAPI.exceptions.ResourceNotFoundException;
import com.STTFV.laundryAPI.repositories.ExpenseRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    @Autowired

    private ExpenseRepository expenseRepository;

    public Expense saveExpense(ExpenseRequest expenseRequest){
        Expense expense = Expense.builder().num(expenseRequest.getNum()).build();
        return expenseRepository.save(expense);
    }

    public Optional<Expense> getExpense(Long expenseId) {
        return expenseRepository.findById(expenseId);
    }

    public List<Expense> getAllExpense() {
        return expenseRepository.findAll();
    }

    public  Expense updateExpense(ExpenseRequest expenseRequest, Long expenseId) {
        Optional<Expense> expense = expenseRepository.findById(expenseId);

        if (expense.isEmpty()) {
            throw new ResourceNotFoundException("Expense not found.");
        }

        expense.get().setNum(expenseRequest.getNum());

        return expenseRepository.save(expense.get());
    }

    public void deleteExpense(Long expenseId) {
        Optional<Expense> expense = expenseRepository.findById(expenseId);

        if (expense.isEmpty()) {
            throw new ResourceNotFoundException("Expense not found.");
        }

        expenseRepository.deleteById(expenseId);
    }
}
