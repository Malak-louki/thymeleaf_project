package com.hb.cda.thymeleafproject.controller;

import com.hb.cda.thymeleafproject.entity.Cart;
import com.hb.cda.thymeleafproject.entity.User;
import com.hb.cda.thymeleafproject.repository.CartRepository;
import com.hb.cda.thymeleafproject.repository.UserRepository;
import com.hb.cda.thymeleafproject.security.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class CartController {
    private final CartService cartService;
    private final CartRepository repo;
    private final UserRepository userRepo;

    public CartController(CartRepository repo, UserRepository userRepo, CartService cartService) {
        this.repo = repo;
        this.userRepo = userRepo;
        this.cartService = cartService;
    }

    @GetMapping("/cart")
    public String cart(Model model, Principal principal) {
        User user = userRepo.findByUsername(principal.getName()).orElseThrow();
        Cart cart = cartService.getCart(user);
        model.addAttribute("cart", cart);
        model.addAttribute("user", user);
        model.addAttribute("total", cart.getTotalPrice(user));
        return "cart";
    }

    @PostMapping ("/cart/add/{productId}")
    public String addToCart(@PathVariable String productId, Model model, Principal principal) {
        User user = userRepo.findByUsername(principal.getName()).orElseThrow();
        cartService.addProductToCart(user, productId);
        return "redirect:/cart";
    }
    @PostMapping("/cart/clear")
    public String clearCart(Principal principal) {
        User user = userRepo.findByUsername(principal.getName()).orElseThrow();
        cartService.clearCart(user);
        return "redirect:/cart";
}
    @PostMapping("/cart/remove/{productId}")
    public String removeFromCart(@PathVariable String productId, Principal principal) {
        User user = userRepo.findByUsername(principal.getName()).orElseThrow();
        cartService.removeProductFtomCart(user, productId);
        return "redirect:/cart";
    }

}
