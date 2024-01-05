package com.STTFV.laundryAPI.controllers;

import com.STTFV.laundryAPI.dto.requests.ImageRequest;
import com.STTFV.laundryAPI.dto.requests.ProductRequest;
import com.STTFV.laundryAPI.entities.Image;
import com.STTFV.laundryAPI.entities.Product;
import com.STTFV.laundryAPI.exceptions.ResourceNotFoundException;
import com.STTFV.laundryAPI.services.ImageService;
import com.STTFV.laundryAPI.services.ProductService;
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
@RequestMapping("api/v1/product")
public class ProductController {



    @Autowired

    private ProductService productService;

    @PostMapping("")
    @Operation(summary = "Create product", tags = "Product")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Product Created"),
            @ApiResponse(responseCode = "500", description = "When server error"),
            @ApiResponse(responseCode = "401", description = "User is not authenticated")
    })

    private ResponseEntity<Product> createProduct (@RequestBody @Valid ProductRequest productRequest) {
        return new ResponseEntity<>(productService.saveProduct(productRequest), HttpStatus.CREATED);
    }


    @GetMapping("")
    @Operation(summary = "Get the list of products", tags = "Product")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Products fetched"),
            @ApiResponse(responseCode = "204", description = "Products are empty"),
            @ApiResponse(responseCode = "500", description = "When server error"),
            @ApiResponse(responseCode = "401", description = "User is not authenticated")
    })

    private ResponseEntity<List<Product>> getProducts () {
        List<Product> products = productService.getAllProduct();

        if (products.isEmpty()) {
            return new ResponseEntity<>(products, HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity<>(products, HttpStatus.OK);
        }
    }

    @GetMapping("/{productId}")
    @Operation(summary = "Get a product", tags = "Product")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product fetched"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "When server error"),
            @ApiResponse(responseCode = "401", description = "User is not authenticated")
    })
    private ResponseEntity<Product> getProduct (@PathVariable Long productId) {
        Optional<Product> product = productService.getProduct(productId);

        if (product.isPresent()) {
            return new ResponseEntity<>(product.get(), HttpStatus.OK);
        }
        else {
            throw new ResourceNotFoundException("Product not found");
        }
    }

    @PutMapping("/{productId}")
    @Operation(summary = "Get a Product", tags = "Product")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product updated"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "When server error"),
            @ApiResponse(responseCode = "401", description = "User is not authenticated")
    })
    private ResponseEntity<Product> updateProduct (@RequestBody @Valid ProductRequest productRequest, @PathVariable Long productId) {
        return new ResponseEntity<>(productService.updateProduct(productRequest, productId), HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    @Operation(summary = "Delete a product", tags = "Product")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product deleted"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "When server error"),
            @ApiResponse(responseCode = "401", description = "User is not authenticated")
    })
    private ResponseEntity<Void> deleteProduct (@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
