package io.grocery.backend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import io.grocery.backend.dto.ProductRequest;
import io.grocery.backend.entity.Products;
import io.grocery.backend.repository.ProductsRepository;

@Service
public class ProductService {

    @Autowired
    private ProductsRepository productsRepository;

    public boolean addProduct(ProductRequest req) {
        if (productsRepository.findByTitle(req.getProductName()).isPresent()) {
            return false;
        }

        Products product = Products.builder()
                .title(req.getProductName())
                .price(req.getProductPrice())
                .description(req.getProductDesc())
                .image(req.getImage())
                .quantity(req.getQuantity())
                .build();

        productsRepository.save(product);
        return true;
    }

    public ResponseEntity<Iterable<Products>> getAllProducts() {
        return ResponseEntity.status(200).body(productsRepository.findAll());
    }

    public boolean deleteProduct(String title) {
        Optional<Products> productToDelete = productsRepository.findByTitle(title);

        if (productToDelete.isPresent()) {
            productsRepository.delete(productToDelete.get());
            return true;
        } else {
            return false;
        }
    }

}
