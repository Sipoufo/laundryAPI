package com.STTFV.laundryAPI.controllers;


import com.STTFV.laundryAPI.dto.requests.CustomerRequest;
import com.STTFV.laundryAPI.dto.requests.ExpenseRequest;
import com.STTFV.laundryAPI.dto.responses.DataResponse;
import com.STTFV.laundryAPI.entities.Customer;
import com.STTFV.laundryAPI.entities.Expense;
import com.STTFV.laundryAPI.exceptions.ResourceNotFoundException;
import com.STTFV.laundryAPI.services.CustomerService;
import com.STTFV.laundryAPI.services.ExpenseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*" ,maxAge = 3600)
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/expense")
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;

    @PostMapping("")
    @Operation(summary = "Create expense", tags = "Expense")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Expense Created"),
            @ApiResponse(responseCode = "500", description = "When server error"),
            @ApiResponse(responseCode = "401", description = "User is not authenticated")
    })

    private ResponseEntity<Expense> createExpense (@RequestBody @Valid ExpenseRequest expenseRequest) {
        return new ResponseEntity<>(expenseService.saveExpense(expenseRequest), HttpStatus.CREATED);
    }


    @GetMapping("/{pageNumber}/{pageSize}")
    @Operation(summary = "Get the list of expenses", tags = "Expense")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Expenses fetched"),
            @ApiResponse(responseCode = "204", description = "Expenses are empty"),
            @ApiResponse(responseCode = "500", description = "When server error"),
            @ApiResponse(responseCode = "401", description = "User is not authenticated")
    })

    private ResponseEntity<DataResponse> getExpenses (@PathVariable("pageNumber") int pageNumber, @PathVariable("pageSize") int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber-1, pageSize);
        List<Expense> expenses = expenseService.getAllExpense(pageable);

        DataResponse expensesResponse = DataResponse
                .builder()
                .data(expenses)
                .pageable(pageable)
                .build();

        if (expenses.isEmpty()) {
            return new ResponseEntity<>(expensesResponse, HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity<>(expensesResponse, HttpStatus.OK);
        }
    }

    @GetMapping("/{expenseId}")
    @Operation(summary = "Get a expense", tags = "Expense")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Expense fetched"),
            @ApiResponse(responseCode = "404", description = "Expense not found"),
            @ApiResponse(responseCode = "500", description = "When server error"),
            @ApiResponse(responseCode = "401", description = "User is not authenticated")
    })
    private ResponseEntity<Expense> getExpense (@PathVariable Long expenseId) {
        Optional<Expense> expense = expenseService.getExpense(expenseId);

        if (expense.isPresent()) {
            return new ResponseEntity<>(expense.get(), HttpStatus.OK);
        }
        else {
            throw new ResourceNotFoundException("Expense not found");
        }
    }

    @PutMapping("/{expenseId}")
    @Operation(summary = "Get a expense", tags = "Expense")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Expense updated"),
            @ApiResponse(responseCode = "404", description = "Expense not found"),
            @ApiResponse(responseCode = "500", description = "When server error"),
            @ApiResponse(responseCode = "401", description = "User is not authenticated")
    })
    private ResponseEntity<Expense> updateExpense (@RequestBody @Valid ExpenseRequest expenseRequest, @PathVariable Long expenseId) {
        return new ResponseEntity<>(expenseService.updateExpense(expenseRequest, expenseId), HttpStatus.OK);
    }

    @DeleteMapping("/{expenseId}")
    @Operation(summary = "Delete a expense", tags = "Expense")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Expense deleted"),
            @ApiResponse(responseCode = "404", description = "Expense not found"),
            @ApiResponse(responseCode = "500", description = "When server error"),
            @ApiResponse(responseCode = "401", description = "User is not authenticated")
    })
    private ResponseEntity<Void> deleteExpense (@PathVariable Long expenseId) {
        expenseService.deleteExpense(expenseId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
