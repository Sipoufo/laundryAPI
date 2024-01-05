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
                .image(productRequest.getImage())
                .normalIroning(productRequest.getNormalIroning())  // Utilisation du nom de variable correct
                .fastIroning(productRequest.getFastIroning())
                .fastDetergent(productRequest.getFastDetergent())
                .normalGloriousPressing(productRequest.getNormalGloriousPressing())
                .build();

        return productRepository.save(product);
    }


    public Optional<Product> getProduct(Long productId) {
        return productRepository.findById(productId);
    }

    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    public  Product updateProduct(ProductRequest productRequest, Long productId) {
        Optional<Product> product = productRepository.findById(productId);

        if (product.isEmpty()) {
            throw new ResourceNotFoundException("product not found.");
        }

        product.get().setName(productRequest.getName());

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
