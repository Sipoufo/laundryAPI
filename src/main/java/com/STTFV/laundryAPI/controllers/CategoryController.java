package com.STTFV.laundryAPI.controllers;

import com.STTFV.laundryAPI.dto.requests.CategoryRequest;
import com.STTFV.laundryAPI.entities.Category;
import com.STTFV.laundryAPI.exceptions.ResourceNotFoundException;
import com.STTFV.laundryAPI.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/category")
@Slf4j
@RequiredArgsConstructor
public class CategoryController {
    private CategoryService categoryService;

    @PostMapping("")
    @Operation(summary = "Create category", tags = "Category")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Category Created"),
            @ApiResponse(responseCode = "500", description = "When server error"),
            @ApiResponse(responseCode = "401", description = "User is not authenticated")
    })
    private ResponseEntity<Category> createCategory (@RequestBody @Valid CategoryRequest categoryRequest) {
        return new ResponseEntity<>(categoryService.saveCategory(categoryRequest), HttpStatus.CREATED);
    }

    @GetMapping("")
    @Operation(summary = "Get the list of categories", tags = "Category")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categories fetched"),
            @ApiResponse(responseCode = "204", description = "Categories are empty"),
            @ApiResponse(responseCode = "500", description = "When server error"),
            @ApiResponse(responseCode = "401", description = "User is not authenticated")
    })
    private ResponseEntity<List<Category>> getCategories () {
        List<Category> categories = categoryService.getAllCategory();

        if (categories.isEmpty()) {
            return new ResponseEntity<>(categories, HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity<>(categories, HttpStatus.OK);
        }
    }

    @GetMapping("/{categoryId}")
    @Operation(summary = "Get a category", tags = "Category")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Category fetched"),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "500", description = "When server error"),
            @ApiResponse(responseCode = "401", description = "User is not authenticated")
    })
    private ResponseEntity<Category> getCategory (@PathVariable Long CategoryId) {
        Optional<Category> category = categoryService.getCategory(CategoryId);

        if (category.isPresent()) {
            return new ResponseEntity<>(category.get(), HttpStatus.OK);
        }
        else {
            throw new ResourceNotFoundException("Category not found");
        }
    }

    @PutMapping("/{categoryId}")
    @Operation(summary = "Get a category", tags = "Category")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Category updated"),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "500", description = "When server error"),
            @ApiResponse(responseCode = "401", description = "User is not authenticated")
    })
    private ResponseEntity<Category> updateCategory (@RequestBody @Valid CategoryRequest categoryRequest, @PathVariable Long CategoryId) {
        return new ResponseEntity<>(categoryService.updateCategory(categoryRequest, CategoryId), HttpStatus.OK);
    }

    @DeleteMapping("/{categoryId}")
    @Operation(summary = "Delete a category", tags = "Category")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Category deleted"),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "500", description = "When server error"),
            @ApiResponse(responseCode = "401", description = "User is not authenticated")
    })
    private ResponseEntity<Void> deleteCategory (@PathVariable Long CategoryId) {
        categoryService.deleteCategory(CategoryId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
