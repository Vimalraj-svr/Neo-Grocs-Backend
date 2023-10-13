package io.grocery.backend.service;

import io.grocery.backend.dto.CartItemDto;
import io.grocery.backend.dto.CartItemResponseDto;
import io.grocery.backend.dto.CustomerDetailsDto;
import io.grocery.backend.entity.CartItem;
import io.grocery.backend.entity.Products;
import io.grocery.backend.entity.User;
import io.grocery.backend.repository.CartItemrepository;
import io.grocery.backend.repository.ProductsRepository;
import io.grocery.backend.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartItemService {
    private final CartItemrepository cartItemRepository;
    private final ProductsRepository productRepository;
    private final UserRepository userRepository;

    @Autowired
    public CartItemService(CartItemrepository cartItemRepository, ProductsRepository productRepository,
            UserRepository userRepository) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
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

    public List<CustomerDetailsDto> getCustomerDetailsWithCartProducts() {
        List<CustomerDetailsDto> customerDetailsList = new ArrayList<>();
        List<User> users = StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    
        for (User user : users) {
            CustomerDetailsDto customerDetailsDto = new CustomerDetailsDto();
            customerDetailsDto.setUserId(user.getUid());
            customerDetailsDto.setUsername(user.getName());
            List<CartItem> cartItems = cartItemRepository.findByUser(user);
            List<CartItemDto> cartItemDtos = cartItems.stream()
                    .map(cartItem -> {
                        CartItemDto cartItemDto = new CartItemDto(cartItem.getProduct());
                        cartItemDto.setQuantity(cartItem.getQuantity());
                        cartItemDto.setImage(cartItem.getProduct().getImage()); // Assuming you have an 'image' property in your Products entity
                        return cartItemDto;
                    })
                    .collect(Collectors.toList());
            customerDetailsDto.setCartItems(cartItemDtos);
            customerDetailsList.add(customerDetailsDto);
        }
    
        return customerDetailsList;
    }
    


}
