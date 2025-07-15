package com.hb.cda.thymeleafproject.controller;

import com.hb.cda.thymeleafproject.entity.Cart;
import com.hb.cda.thymeleafproject.entity.CartItem;
import com.hb.cda.thymeleafproject.entity.User;
import com.hb.cda.thymeleafproject.repository.CartItemRepository;
import com.hb.cda.thymeleafproject.repository.CartRepository;
import com.hb.cda.thymeleafproject.repository.ProductRepository;
import com.hb.cda.thymeleafproject.repository.UserRepository;
import com.hb.cda.thymeleafproject.security.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class CartController {
    private final CartService cartService;
    private final CartRepository cartRepo;
    private final UserRepository userRepo;
    private final ProductRepository productRepo;
    private final CartItemRepository cartItemRepo;

    public CartController(CartRepository repo, UserRepository userRepo, CartService cartService, ProductRepository productRepo, CartItemRepository cartItemRepo) {
        this.cartRepo = repo;
        this.userRepo = userRepo;
        this.cartService = cartService;
        this.productRepo = productRepo;
        this.cartItemRepo = cartItemRepo;
    }

    @GetMapping("/cart")
    public String cart(Model model, Principal principal) {
        User user = userRepo.findByUsername(principal.getName()).orElseThrow();
        Cart cart = cartService.getCart(user);
        List<CartItem> cartItems = cartItemRepo.findByCartId(cart.getId());
        model.addAttribute("cart", cart);
        model.addAttribute("user", user);
        model.addAttribute("items", cartItems );
        model.addAttribute("total", cartService.getTotalPrice(user));
        return "cart";
    }

    @PostMapping ("/cart/add/{productId}")
    public String addToCart(@PathVariable String productId, Model model, Principal principal) {
        User user = userRepo.findByUsername(principal.getName()).orElseThrow();
        cartService.addProductToCart(user, productId);
        return "redirect:/";
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
        cartService.removeProductFromCart(user, productId);
        return "redirect:/cart";
    }

    @GetMapping("/")
    public String index(Model model, Principal principal) {
        User user = userRepo.findByUsername(principal.getName()).orElseThrow();
        model.addAttribute("user", user);
        model.addAttribute("cart", cartService.getCart(user));
        model.addAttribute("products", productRepo.findAll());
        return "index";
    }
}
