package io.grocery.backend.service;

import io.grocery.backend.dto.CartItemDto;
import io.grocery.backend.dto.CartItemResponseDto;
import io.grocery.backend.entity.CartItem;
import io.grocery.backend.entity.Products;
import io.grocery.backend.entity.User;
import io.grocery.backend.repository.CartItemrepository;
import io.grocery.backend.repository.ProductsRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartItemService {
    private final CartItemrepository cartItemRepository;
    private final ProductsRepository productRepository;

    @Autowired
    public CartItemService(CartItemrepository cartItemRepository, ProductsRepository productRepository) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public void addToCart(User user, CartItemDto cartItemDTO) {
        Products product = productRepository.findById(cartItemDTO.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
    
        Optional<CartItem> existingCartItem = cartItemRepository.findByUserAndProduct(user, product);
    
        if (existingCartItem.isPresent()) {
            CartItem cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + cartItemDTO.getQuantity());
            cartItemRepository.save(cartItem);
        } else {
            CartItem cartItem = CartItem.builder()
                    .user(user)
                    .product(product)
                    .quantity(cartItemDTO.getQuantity())
                    .build();
            cartItemRepository.save(cartItem);
        }
    }
    

       public List<CartItemResponseDto> retrieveCart(User user) {
        return cartItemRepository.findByUser(user).stream()
                .map(cartItem -> {
                    CartItemResponseDto dto = new CartItemResponseDto();
                    dto.setCartItemId(cartItem.getId());
                    dto.setProduct(cartItem.getProduct());
                    dto.setQuantity(cartItem.getQuantity());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
