package io.grocery.backend.controller;

import io.grocery.backend.dto.CartItemDto;
import io.grocery.backend.dto.CartItemResponseDto;
import io.grocery.backend.entity.User;
import io.grocery.backend.service.CartItemService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartItemController {
    private final CartItemService cartItemService;

    @Autowired
    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PostMapping("/add")
    public void addToCart(@AuthenticationPrincipal User user, @RequestBody CartItemDto cartItemDTO) {
        cartItemService.addToCart(user, cartItemDTO);
    }

    @GetMapping("/get")
    public List<CartItemResponseDto> retrieveCart(@AuthenticationPrincipal User user) {
        return cartItemService.retrieveCart(user);
    }

}
