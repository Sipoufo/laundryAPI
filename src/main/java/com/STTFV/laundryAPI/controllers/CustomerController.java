package com.STTFV.laundryAPI.controllers;

import com.STTFV.laundryAPI.dto.requests.CategoryRequest;
import com.STTFV.laundryAPI.dto.requests.CustomerRequest;
import com.STTFV.laundryAPI.entities.Category;
import com.STTFV.laundryAPI.entities.Customer;
import com.STTFV.laundryAPI.exceptions.ResourceNotFoundException;
import com.STTFV.laundryAPI.repositories.CustomerRepository;
import com.STTFV.laundryAPI.services.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*" ,maxAge = 3600)
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/customer")
public class CustomerController {

    @Autowired

    private CustomerService customerService;

    @PostMapping("")
    @Operation(summary = "Create customer", tags = "Customer")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Customer Created"),
            @ApiResponse(responseCode = "500", description = "When server error"),
            @ApiResponse(responseCode = "401", description = "User is not authenticated")
    })

    private ResponseEntity<Customer> createCustomer (@RequestBody @Valid CustomerRequest customerRequest) {
        return new ResponseEntity<>(customerService.saveCustomer(customerRequest), HttpStatus.CREATED);
    }


    @GetMapping("")
    @Operation(summary = "Get the list of customers", tags = "Customer")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Customers fetched"),
            @ApiResponse(responseCode = "204", description = "Customers are empty"),
            @ApiResponse(responseCode = "500", description = "When server error"),
            @ApiResponse(responseCode = "401", description = "User is not authenticated")
    })

    private ResponseEntity<List<Customer>> getCustomers () {
        List<Customer> customers = customerService.getAllCustomer();

        if (customers.isEmpty()) {
            return new ResponseEntity<>(customers, HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity<>(customers, HttpStatus.OK);
        }
    }

    @GetMapping("/{customerId}")
    @Operation(summary = "Get a customer", tags = "Customer")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Customer fetched"),
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "500", description = "When server error"),
            @ApiResponse(responseCode = "401", description = "User is not authenticated")
    })
    private ResponseEntity<Customer> getCustomer (@PathVariable Long customerId) {
        Optional<Customer> customer = customerService.getCustomer(customerId);

        if (customer.isPresent()) {
            return new ResponseEntity<>(customer.get(), HttpStatus.OK);
        }
        else {
            throw new ResourceNotFoundException("Customer not found");
        }
    }

    @PutMapping("/{customerId}")
    @Operation(summary = "Get a customer", tags = "Customer")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Customer updated"),
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "500", description = "When server error"),
            @ApiResponse(responseCode = "401", description = "User is not authenticated")
    })
    private ResponseEntity<Customer> updateCustomer (@RequestBody @Valid CustomerRequest customerRequest, @PathVariable Long customerId) {
        return new ResponseEntity<>(customerService.updateCustomer(customerRequest, customerId), HttpStatus.OK);
    }

    @DeleteMapping("/{customerId}")
    @Operation(summary = "Delete a customer", tags = "Customer")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Customer deleted"),
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "500", description = "When server error"),
            @ApiResponse(responseCode = "401", description = "User is not authenticated")
    })
    private ResponseEntity<Void> deleteCustomer (@PathVariable Long customerId) {
        customerService.deleteCustomer(customerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
