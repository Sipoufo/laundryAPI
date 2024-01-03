package com.STTFV.laundryAPI.services;

import com.STTFV.laundryAPI.dto.requests.CategoryRequest;
import com.STTFV.laundryAPI.entities.Category;
import com.STTFV.laundryAPI.exceptions.ResourceNotFoundException;
import com.STTFV.laundryAPI.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public Category saveCategory(CategoryRequest categoryRequest) {
        Category category = Category.builder().name(categoryRequest.getName()).build();
        return categoryRepository.save(category);
    }

    public Optional<Category> getCategory(Long categoryId) {
        return categoryRepository.findById(categoryId);
    }

    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    public Category updateCategory(CategoryRequest categoryRequest, Long categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);

        if (category.isEmpty()) {
            throw new ResourceNotFoundException("Category not found.");
        }

        category.get().setName(categoryRequest.getName());

        return categoryRepository.save(category.get());
    }

    public void deleteCategory(Long categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);

        if (category.isEmpty()) {
            throw new ResourceNotFoundException("Category not found.");
        }

        categoryRepository.deleteById(categoryId);
    }
}
