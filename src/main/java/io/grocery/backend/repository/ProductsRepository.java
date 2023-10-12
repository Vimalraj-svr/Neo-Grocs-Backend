package io.grocery.backend.repository;

import org.springframework.data.repository.CrudRepository;

import io.grocery.backend.entity.Products;

import java.util.Optional;



public interface ProductsRepository extends CrudRepository<Products, String> {
    Optional<Products> findByTitle(String title);
    void deleteByTitle(String title);
    Optional<Products> findById(Long id);
}
