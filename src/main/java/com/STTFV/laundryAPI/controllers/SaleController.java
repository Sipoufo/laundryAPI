package com.STTFV.laundryAPI.controllers;


import com.STTFV.laundryAPI.dto.requests.ProductRequest;
import com.STTFV.laundryAPI.dto.requests.SaleRequest;
import com.STTFV.laundryAPI.entities.Product;
import com.STTFV.laundryAPI.entities.Sale;
import com.STTFV.laundryAPI.exceptions.ResourceNotFoundException;
import com.STTFV.laundryAPI.services.ProductService;
import com.STTFV.laundryAPI.services.SaleService;
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
@RequestMapping("api/v1/sale")
public class SaleController {

    @Autowired

    private SaleService saleService;

    @PostMapping("")
    @Operation(summary = "Create sale", tags = "Sale")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Sale Created"),
            @ApiResponse(responseCode = "500", description = "When server error"),
            @ApiResponse(responseCode = "401", description = "User is not authenticated")
    })

    private ResponseEntity<Sale> createSale (@RequestBody @Valid SaleRequest saleRequest) {
        return new ResponseEntity<>(saleService.saveSale(saleRequest), HttpStatus.CREATED);
    }


    @GetMapping("")
    @Operation(summary = "Get the list of sales", tags = "Sale")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sales fetched"),
            @ApiResponse(responseCode = "204", description = "Sales are empty"),
            @ApiResponse(responseCode = "500", description = "When server error"),
            @ApiResponse(responseCode = "401", description = "User is not authenticated")
    })

    private ResponseEntity<List<Sale>> getSales () {
        List<Sale> sales = saleService.getAllSale();

        if (sales.isEmpty()) {
            return new ResponseEntity<>(sales, HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity<>(sales, HttpStatus.OK);
        }
    }

    @GetMapping("/{saleId}")
    @Operation(summary = "Get a sale", tags = "Sale")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sale fetched"),
            @ApiResponse(responseCode = "404", description = "Sale not found"),
            @ApiResponse(responseCode = "500", description = "When server error"),
            @ApiResponse(responseCode = "401", description = "User is not authenticated")
    })
    private ResponseEntity<Sale> getSale (@PathVariable Long saleId) {
        Optional<Sale> sale = saleService.getSale(saleId);

        if (sale.isPresent()) {
            return new ResponseEntity<>(sale.get(), HttpStatus.OK);
        }
        else {
            throw new ResourceNotFoundException("Sale not found");
        }
    }

    @PutMapping("/{saleId}")
    @Operation(summary = "Get a Sale", tags = "Sale")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sale updated"),
            @ApiResponse(responseCode = "404", description = "Sale not found"),
            @ApiResponse(responseCode = "500", description = "When server error"),
            @ApiResponse(responseCode = "401", description = "User is not authenticated")
    })
    private ResponseEntity<Sale> updateSale (@RequestBody @Valid SaleRequest saleRequest, @PathVariable Long saleId) {
        return new ResponseEntity<>(saleService.updateSale(saleRequest, saleId), HttpStatus.OK);
    }

    @DeleteMapping("/{saleId}")
    @Operation(summary = "Delete a sale", tags = "Sale")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sale deleted"),
            @ApiResponse(responseCode = "404", description = "Sale not found"),
            @ApiResponse(responseCode = "500", description = "When server error"),
            @ApiResponse(responseCode = "401", description = "User is not authenticated")
    })

    private ResponseEntity<Void> deleteSale (@PathVariable Long saleId) {
        saleService.deleteSale(saleId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
