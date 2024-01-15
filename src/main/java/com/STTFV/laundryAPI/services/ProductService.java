package com.STTFV.laundryAPI.services;

import com.STTFV.laundryAPI.dto.requests.ExpenseRequest;
import com.STTFV.laundryAPI.dto.requests.ImageRequest;
import com.STTFV.laundryAPI.dto.requests.ProductRequest;
import com.STTFV.laundryAPI.entities.Expense;
import com.STTFV.laundryAPI.entities.Image;
import com.STTFV.laundryAPI.entities.Product;
import com.STTFV.laundryAPI.exceptions.ResourceNotFoundException;
import com.STTFV.laundryAPI.repositories.ProductRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    @Autowired

    private ProductRepository productRepository;


    public Product saveProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .normalIroning(productRequest.getNormalIroning())  // Utilisation du nom de variable correct
                .fastIroning(productRequest.getFastIroning())
                .normalDetergent(productRequest.getNormalDetergent())
                .fastDetergent(productRequest.getFastDetergent())
                .normalGloriousPressing(productRequest.getNormalGloriousPressing())
                .fastGloriousPressing(productRequest.getFastGloriousPressing())
                .build();

        return productRepository.save(product);
    }


    public Optional<Product> getProduct(Long productId) {
        return productRepository.findById(productId);
    }

    public List<Product> getAllProduct(Pageable pageable) {
        return productRepository.findAll(pageable).getContent();
    }

    public  Product updateProduct(ProductRequest productRequest, Long productId) {
        Optional<Product> product = productRepository.findById(productId);

        if (product.isEmpty()) {
            throw new ResourceNotFoundException("Product not found.");
        }

        product.get().setName(productRequest.getName());
        product.get().setNormalIroning(productRequest.getNormalIroning());
        product.get().setFastIroning(productRequest.getFastIroning());
        product.get().setNormalDetergent(productRequest.getNormalDetergent());
        product.get().setFastDetergent(productRequest.getFastDetergent());
        product.get().setNormalGloriousPressing(productRequest.getNormalGloriousPressing());
        product.get().setFastGloriousPressing(productRequest.getFastGloriousPressing());

        return productRepository.save(product.get());
    }

    public void deleteProduct(Long productId) {
        Optional<Product> product = productRepository.findById(productId);

        if (product.isEmpty()) {
            throw new ResourceNotFoundException("Product not found.");
        }

        productRepository.deleteById(productId);
    }


}
