package io.grocery.backend.repository;

import io.grocery.backend.entity.CartItem;
import io.grocery.backend.entity.Products;
import io.grocery.backend.entity.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemrepository extends JpaRepository<CartItem, String> {
    Optional<CartItem> findByUserAndProduct(User user, Products product);
    List<CartItem> findByUser(User user);
}
