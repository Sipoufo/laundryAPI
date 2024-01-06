package com.STTFV.laundryAPI.services;


import com.STTFV.laundryAPI.dto.requests.CustomerRequest;
import com.STTFV.laundryAPI.dto.requests.ExpenseRequest;
import com.STTFV.laundryAPI.entities.Category;
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
    @Autowired
    private CategoryService categoryService;

    public Expense saveExpense(ExpenseRequest expenseRequest) {
        Optional<Category> category = categoryService.getCategory(expenseRequest.getCategoryId());
        if (category.isEmpty()) {
            throw new ResourceNotFoundException("Category not found");
        }

        Expense expense = Expense.builder()
                .price(expenseRequest.getPrice())
                .isDelivered(expenseRequest.isDelivered())  // Utilisation du getter isDelivered()
                .category(category.get())
                .title(expenseRequest.getTitle())
                .build();

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

        Optional<Category> category = categoryService.getCategory(expenseRequest.getCategoryId());
        if (category.isEmpty()) {
            throw new ResourceNotFoundException("Category not found");
        }

        expense.get().setTitle(expenseRequest.getTitle());
        expense.get().setPrice(expenseRequest.getPrice());
        expense.get().setDelivered(expenseRequest.isDelivered());
        expense.get().setCategory(category.get());

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
