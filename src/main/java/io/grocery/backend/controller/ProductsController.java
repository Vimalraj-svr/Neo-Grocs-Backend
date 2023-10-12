package io.grocery.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.grocery.backend.dto.ProductRequest;
import io.grocery.backend.entity.Products;
import io.grocery.backend.service.ProductService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ProductsController {

    @Autowired
    private ProductService productService;

    @PostMapping("/admin/addproduct")
    public ResponseEntity<String> addProduct(@RequestBody ProductRequest product) {
        if (productService.addProduct(product)) {
            return ResponseEntity.status(200).body("Product added to inventory");
        }
        return ResponseEntity.badRequest().build();
    }

    //@CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/allproducts")
    public @ResponseBody ResponseEntity<Iterable<Products>> getAllProducts() {
        return productService.getAllProducts();
    }

    //@CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/removeproduct")
    public ResponseEntity<String> deleteProduct(@RequestBody String title) {
        if (productService.deleteProduct(title)) {
            return ResponseEntity.status(200).body("Product deleted from inventory");
        } else {
            return ResponseEntity.status(200).body("Product is not deleted from inventory");
        }
    }

    @GetMapping("/user/allproducts")
    public @ResponseBody ResponseEntity<Iterable<Products>> getAllUserProducts() {
        return productService.getAllProducts();
    }
}
